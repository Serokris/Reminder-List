package com.example.remindersaboutmeetingswithclients.presentation.reminderlist

import android.app.AlertDialog
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
import com.example.remindersaboutmeetingswithclients.presentation.viewmodels.ReminderViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReminderListFragment : Fragment() {

    private val viewModel: ReminderViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentReminderListBinding.inflate(inflater)
        val navController = findNavController()
        val adapter = ReminderListAdapter(requireContext())

        viewModel.getAllReminders().observe(viewLifecycleOwner, { list ->
            adapter.submitList(list)
            binding.reminderImage.visibility = if (list.isEmpty()) View.VISIBLE else View.INVISIBLE
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
                deleteReminder(reminder)
            }
        }).attachToRecyclerView(binding.reminderRecyclerView)

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.deleteAllRemindersAction -> deleteAllReminders()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteAllReminders() {
        viewModel.getAllReminders().observeOnce { list ->
            if (list.isEmpty()) {
                Toast.makeText(requireContext(), R.string.you_have_no_reminders,
                    Toast.LENGTH_SHORT).show()
            } else {
                AlertDialog.Builder(requireContext())
                    .setTitle(R.string.confirm_action)
                    .setMessage("Delete all reminders?")
                    .setPositiveButton("Confirm") { dialog, _ ->
                        viewModel.deleteAllReminders()
                        dialog.dismiss()
                    }
                    .setNegativeButton("Cancel") { dialog, _ ->
                        dialog.dismiss()
                    }.create().show()
            }
        }
    }

    private fun deleteReminder(reminder: ReminderItem) {
        viewModel.delete(reminder)
        Snackbar.make(requireView(), "Reminder deleted", Snackbar.LENGTH_LONG).apply {
            setAction("Undo") {
                viewModel.insert(reminder)
            }
            show()
        }
    }
}