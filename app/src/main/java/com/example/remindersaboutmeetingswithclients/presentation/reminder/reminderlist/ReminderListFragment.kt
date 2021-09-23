package com.example.remindersaboutmeetingswithclients.presentation.reminder.reminderlist

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.remindersaboutmeetingswithclients.R
import com.example.remindersaboutmeetingswithclients.databinding.FragmentReminderListBinding
import com.example.remindersaboutmeetingswithclients.domain.models.ReminderItem
import com.example.remindersaboutmeetingswithclients.utils.observeOnce
import com.example.remindersaboutmeetingswithclients.utils.ReminderAlarmManager
import com.example.remindersaboutmeetingswithclients.utils.SharedPreferenceUtils
import com.example.remindersaboutmeetingswithclients.utils.constants.CreateReminderFragmentConstants.CREATE_REMINDER_FRAGMENT_PREF_NAME
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReminderListFragment : Fragment() {

    private val viewModel: ReminderListViewModel by viewModels()
    private lateinit var binding: FragmentReminderListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentReminderListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        SharedPreferenceUtils.clearSharedPreferenceIfPreferenceIsSet(
            requireContext(),
            CREATE_REMINDER_FRAGMENT_PREF_NAME
        )

        val navController = findNavController()
        val adapter = ReminderListAdapter(requireContext())

        viewModel.getAllReminders().observe(viewLifecycleOwner, { list ->
            adapter.submitList(list)
            binding.reminderImage.visibility =
                if (list.isEmpty()) View.VISIBLE else View.INVISIBLE
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
        }.addCallback(object : BaseTransientBottomBar.BaseCallback<Snackbar>() {
            override fun onShown(transientBottomBar: Snackbar?) {
                super.onShown(transientBottomBar)
            }

            override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                super.onDismissed(transientBottomBar, event)
                if (event == Snackbar.Callback.DISMISS_EVENT_TIMEOUT) {
                    ReminderAlarmManager.cancelAlarm(requireContext(), reminder.requestCode)
                }
            }
        }).show()
    }
}