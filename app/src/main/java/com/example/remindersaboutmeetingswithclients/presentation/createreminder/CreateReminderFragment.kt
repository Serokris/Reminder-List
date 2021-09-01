package com.example.remindersaboutmeetingswithclients.presentation.createreminder

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.remindersaboutmeetingswithclients.R
import com.example.remindersaboutmeetingswithclients.databinding.FragmentCreateReminderBinding
import android.app.DatePickerDialog
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import com.example.remindersaboutmeetingswithclients.domain.models.ReminderItem
import com.example.remindersaboutmeetingswithclients.utils.ReminderAlarmManager
import com.example.remindersaboutmeetingswithclients.presentation.viewmodels.ReminderViewModel
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import dagger.hilt.android.AndroidEntryPoint
import java.text.DateFormat
import java.util.*

@AndroidEntryPoint
class CreateReminderFragment : Fragment() {

    private val viewModel: ReminderViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentCreateReminderBinding.inflate(inflater)
        val navController = findNavController()
        val client = CreateReminderFragmentArgs.fromBundle(requireArguments()).client

        binding.apply {
            selectClientButton.setOnClickListener {
                navController.navigate(R.id.clientListFragment)
            }

            if (client != null) {
                Glide.with(requireContext()).load(client.picture.large).into(selectedClientImage)
                fullNameSelectedClient.text =
                    "${client.fullName.firstName} ${client.fullName.lastName}"
                emailSelectedClient.text = client.email
            }

            val calendar = Calendar.getInstance()

            setDateSwitch.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    val mYear = calendar.get(Calendar.YEAR)
                    val mMonth = calendar.get(Calendar.MONTH)
                    val mDay = calendar.get(Calendar.DAY_OF_MONTH)

                    val datePickedDialog =
                        DatePickerDialog(requireActivity(), { _, year, month, dayOfMonth ->
                            selectedDateText.text = "$year/$month/$dayOfMonth"
                            calendar.set(Calendar.YEAR, year)
                            calendar.set(Calendar.MONTH, month)
                            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                        }, mYear, mMonth, mDay)

                    datePickedDialog.setOnCancelListener { setDateSwitch.isChecked = false }
                    datePickedDialog.show()
                } else selectedDateText.text = String()
            }

            setTimeSwitch.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    val timePicker = MaterialTimePicker.Builder()
                        .setTimeFormat(TimeFormat.CLOCK_24H)
                        .setHour(12)
                        .setMinute(0)
                        .setTitleText(R.string.select_time_for_reminder)
                        .build()

                    timePicker.addOnPositiveButtonClickListener {
                        calendar.set(Calendar.MILLISECOND, 0)
                        calendar.set(Calendar.SECOND, 0)
                        calendar.set(Calendar.MINUTE, timePicker.minute)
                        calendar.set(Calendar.HOUR_OF_DAY, timePicker.hour)

                        selectedTimeText.text =
                            DateFormat.getTimeInstance(DateFormat.SHORT).format(calendar.time)
                    }
                    timePicker.addOnNegativeButtonClickListener { setTimeSwitch.isChecked = false }
                    timePicker.show(childFragmentManager, "time-picker")
                } else selectedTimeText.text = String()
            }

            createReminderButton.setOnClickListener {
                if (titleEditText.text.isNotEmpty() && fullNameSelectedClient.text.isNotEmpty()
                    && selectedDateText.text.isNotEmpty()) {
                    if (dayOrTimeIsValid(calendar)) {
                        val reminder = ReminderItem(
                            0,
                            titleEditText.text.toString(),
                            selectedDateText.text.toString(),
                            selectedTimeText.text.toString(),
                            client!!
                        )
                        viewModel.insert(reminder)

                        if (selectedTimeText.text.isNotEmpty()) {
                            ReminderAlarmManager.createAlarm(requireActivity(), calendar)
                            Toast.makeText(requireContext(), R.string.you_will_receive_notification,
                                Toast.LENGTH_LONG).show()
                        }
                        navController.navigate(R.id.reminderListFragment)
                    } else Toast.makeText(requireContext(), R.string.not_valid_date_or_time,
                        Toast.LENGTH_LONG).show()
                } else Toast.makeText(requireContext(), R.string.fill_in_all_required_fields,
                        Toast.LENGTH_LONG).show()
            }
        }
        return binding.root
    }

    private fun dayOrTimeIsValid(calendar: Calendar): Boolean {
        return !calendar.before(Calendar.getInstance())
    }
}