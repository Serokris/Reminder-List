package com.example.remindersaboutmeetingswithclients.presentation.reminder.reminderlist

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.models.ReminderItem
import com.example.remindersaboutmeetingswithclients.R
import com.example.remindersaboutmeetingswithclients.databinding.FragmentReminderListBinding
import com.example.remindersaboutmeetingswithclients.presentation.base.BaseBindingFragment
import com.example.remindersaboutmeetingswithclients.utils.ReminderAlarmManager
import com.example.remindersaboutmeetingswithclients.utils.appComponent
import com.example.remindersaboutmeetingswithclients.utils.observeOnce
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar

class ReminderListFragment :
    BaseBindingFragment<FragmentReminderListBinding>(FragmentReminderListBinding::inflate) {

    private val viewModel: ReminderListViewModel by viewModels {
        appComponent.viewModelFactory()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    private fun initViews() {
        viewModel.clearViewsStateInCreateReminderFragment()

        val navController = findNavController()
        val adapter = ReminderListAdapter()

        viewModel.getAllReminders().observe(viewLifecycleOwner, { reminderList ->
            adapter.submitList(reminderList)
            binding.reminderImage.visibility =
                if (reminderList.isEmpty()) View.VISIBLE else View.INVISIBLE
        })

        binding.apply {
            reminderRecyclerView.adapter = adapter

            addReminderButton.setOnClickListener {
                navController.navigate(R.id.createReminderFragment)
            }
        }

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val reminder = adapter.currentList[position]
                deleteReminderAndCancelAlarm(reminder)
            }
        }).attachToRecyclerView(binding.reminderRecyclerView)

        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.deleteAllRemindersAction -> deleteAllRemindersAndCancelAllAlarms(requireContext())
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteAllRemindersAndCancelAllAlarms(context: Context) {
        viewModel.getAllReminders().observeOnce { list ->
            if (list.isEmpty()) {
                Toast.makeText(
                    requireContext(), R.string.you_have_no_reminders,
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                AlertDialog.Builder(context)
                    .setTitle(R.string.confirm_action)
                    .setMessage("Delete all reminders?")
                    .setPositiveButton("Confirm") { dialog, _ ->
                        viewModel.deleteAllReminders()
                        ReminderAlarmManager.cancelAllAlarms(context)
                        dialog.dismiss()
                    }
                    .setNegativeButton("Cancel") { dialog, _ ->
                        dialog.dismiss()
                    }.create().show()
            }
        }
    }

    private fun deleteReminderAndCancelAlarm(reminder: ReminderItem) {
        viewModel.delete(reminder)

        Snackbar.make(requireView(), "Reminder deleted", Snackbar.LENGTH_LONG).apply {
            setAction("Undo") {
                viewModel.insert(reminder)
            }
            setBackgroundTint(resources.getColor(R.color.dark_grey))
            setActionTextColor(resources.getColor(R.color.light_red))
            setTextColor(resources.getColor(R.color.white))

        }.addCallback(object : BaseTransientBottomBar.BaseCallback<Snackbar>() {
            override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                super.onDismissed(transientBottomBar, event)

                if (event == Snackbar.Callback.DISMISS_EVENT_TIMEOUT) {
                    ReminderAlarmManager.cancelAlarm(requireContext(), reminder.requestCode)
                }
            }
        }).show()
    }
}