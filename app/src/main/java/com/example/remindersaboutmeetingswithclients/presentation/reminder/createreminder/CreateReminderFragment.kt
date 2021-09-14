package com.example.remindersaboutmeetingswithclients.presentation.reminder.createreminder

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
import android.content.Context
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.remindersaboutmeetingswithclients.domain.models.ReminderItem
import com.example.remindersaboutmeetingswithclients.utils.ReminderAlarmManager
import com.example.remindersaboutmeetingswithclients.presentation.reminder.ReminderViewModel
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import dagger.hilt.android.AndroidEntryPoint
import java.text.DateFormat
import java.util.*
import android.content.Context.MODE_PRIVATE
import com.example.remindersaboutmeetingswithclients.utils.constants.CreateReminderFragmentConstants.CREATE_REMINDER_FRAGMENT_PREF_NAME
import com.example.remindersaboutmeetingswithclients.utils.constants.CreateReminderFragmentConstants.SAVED_CALENDAR_TIME
import com.example.remindersaboutmeetingswithclients.utils.constants.CreateReminderFragmentConstants.SAVED_SELECTED_DATE_TEXT
import com.example.remindersaboutmeetingswithclients.utils.constants.CreateReminderFragmentConstants.SAVED_SELECTED_TIME_TEXT
import com.example.remindersaboutmeetingswithclients.utils.constants.CreateReminderFragmentConstants.SAVED_STATE_OF_DATE_SWITCH
import com.example.remindersaboutmeetingswithclients.utils.constants.CreateReminderFragmentConstants.SAVED_STATE_OF_TIME_SWITCH
import com.example.remindersaboutmeetingswithclients.utils.constants.CreateReminderFragmentConstants.SAVED_TITLE_TEXT
import com.example.remindersaboutmeetingswithclients.utils.notification.ClientMeetingNotification

@AndroidEntryPoint
class CreateReminderFragment : Fragment() {

    private val viewModel: ReminderViewModel by viewModels()
    private lateinit var binding: FragmentCreateReminderBinding
    private lateinit var calendar: Calendar

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateReminderBinding.inflate(inflater)
        calendar = Calendar.getInstance()

        loadStateFragmentDataInPreference(requireContext())

        val navController = findNavController()
        val client = CreateReminderFragmentArgs.fromBundle(requireArguments()).client

        binding.apply {
            selectClientButton.setOnClickListener {
                saveStateFragmentDataInPreference(requireContext())
                navController.navigate(R.id.clientListFragment)
            }

            if (client != null) {
                Glide.with(requireContext()).load(client.picture.large).into(selectedClientImage)
                fullNameSelectedClient.text =
                    "${client.fullName.firstName} ${client.fullName.lastName}"
                emailSelectedClient.text = client.email
            }

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
                } else selectedDateText.text = ""
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
                } else selectedTimeText.text = ""
            }

            createReminderButton.setOnClickListener {
                if (requiredFieldsIsFilled()) {
                    if (dayOrTimeIsValid(calendar)) {
                        val reminder = ReminderItem(
                            0,
                            titleEditText.text.toString(),
                            selectedDateText.text.toString(),
                            selectedTimeText.text.toString(),
                            client!!
                        )

                        if (selectedTimeText.text.isNotEmpty()) {
                            ReminderAlarmManager.createAlarm(requireActivity(), calendar)

                            val currentClientFullName =
                                "${client.fullName.firstName} ${client.fullName.lastName}"
                            ClientMeetingNotification.setCurrentClientFullName(currentClientFullName)

                            reminder.requestCode = ReminderAlarmManager.getCurrentRequestCode

                            Toast.makeText(
                                requireContext(), R.string.you_will_receive_notification,
                                Toast.LENGTH_LONG
                            ).show()
                        }

                        viewModel.insert(reminder)
                        navController.navigate(R.id.reminderListFragment)
                    } else Toast.makeText(
                        requireContext(), R.string.not_valid_date_or_time, Toast.LENGTH_LONG
                    ).show()
                } else Toast.makeText(
                    requireContext(), R.string.fill_in_all_required_fields, Toast.LENGTH_LONG
                ).show()
            }
        }

        return binding.root
    }

    private fun requiredFieldsIsFilled(): Boolean {
        binding.apply {
            val titleEdtIsNotEmpty = titleEditText.text.isNotEmpty()
            val fullNameClientIsNotEmpty = fullNameSelectedClient.text.isNotEmpty()
            val selectedDateTextIsNotEmpty = selectedDateText.text.isNotEmpty()

            return titleEdtIsNotEmpty && fullNameClientIsNotEmpty && selectedDateTextIsNotEmpty
        }
    }

    private fun dayOrTimeIsValid(calendar: Calendar): Boolean {
        return !calendar.before(Calendar.getInstance())
    }

    private fun saveStateFragmentDataInPreference(context: Context) {
        val sharedPreferences =
            context.getSharedPreferences(CREATE_REMINDER_FRAGMENT_PREF_NAME, MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        binding.apply {
            editor.putString(SAVED_TITLE_TEXT, this.titleEditText.text.toString())
            editor.putString(SAVED_SELECTED_DATE_TEXT, this.selectedDateText.text.toString())
            editor.putString(SAVED_SELECTED_TIME_TEXT, this.selectedTimeText.text.toString())
            editor.putBoolean(SAVED_STATE_OF_DATE_SWITCH, this.setDateSwitch.isChecked)
            editor.putBoolean(SAVED_STATE_OF_TIME_SWITCH, this.setTimeSwitch.isChecked)
            editor.putLong(SAVED_CALENDAR_TIME, calendar.timeInMillis)
        }
        editor.apply()
    }

    private fun loadStateFragmentDataInPreference(context: Context) {
        val sharedPreferences =
            context.getSharedPreferences(CREATE_REMINDER_FRAGMENT_PREF_NAME, MODE_PRIVATE)

        val savedTitleEdt = sharedPreferences.getString(SAVED_TITLE_TEXT, "")
        val savedSelectedDateText = sharedPreferences.getString(SAVED_SELECTED_DATE_TEXT, "")
        val savedSelectedTimeText = sharedPreferences.getString(SAVED_SELECTED_TIME_TEXT, "")
        val savedDataSwitch = sharedPreferences.getBoolean(SAVED_STATE_OF_DATE_SWITCH, false)
        val savedTimeSwitch = sharedPreferences.getBoolean(SAVED_STATE_OF_TIME_SWITCH, false)
        val savedCalendarTime =
            sharedPreferences.getLong(SAVED_CALENDAR_TIME, calendar.timeInMillis)

        binding.apply {
            titleEditText.setText(savedTitleEdt)
            selectedDateText.text = savedSelectedDateText
            selectedTimeText.text = savedSelectedTimeText
            setDateSwitch.isChecked = savedDataSwitch
            setTimeSwitch.isChecked = savedTimeSwitch
        }
        calendar.timeInMillis = savedCalendarTime
    }
}