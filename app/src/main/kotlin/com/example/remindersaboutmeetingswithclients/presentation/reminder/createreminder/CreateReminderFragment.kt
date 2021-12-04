package com.example.remindersaboutmeetingswithclients.presentation.reminder.createreminder

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.data.mappers.toClient
import com.example.domain.models.ReminderItem
import com.example.remindersaboutmeetingswithclients.R
import com.example.remindersaboutmeetingswithclients.databinding.FragmentCreateReminderBinding
import com.example.remindersaboutmeetingswithclients.presentation.base.BaseBindingFragment
import com.example.remindersaboutmeetingswithclients.utils.ReminderAlarmManager
import com.example.remindersaboutmeetingswithclients.utils.appComponent
import com.example.remindersaboutmeetingswithclients.utils.notification.ClientDataForNotification
import com.example.remindersaboutmeetingswithclients.utils.notification.ClientMeetingNotification
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.text.DateFormat
import java.util.*

class CreateReminderFragment :
    BaseBindingFragment<FragmentCreateReminderBinding>(FragmentCreateReminderBinding::inflate) {

    private val viewModel: CreateReminderViewModel by viewModels {
        appComponent.viewModelFactory()
    }
    private val calendar by lazy(LazyThreadSafetyMode.NONE) { Calendar.getInstance() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    @SuppressLint("SetTextI18n")
    private fun initViews() {
        val navController = findNavController()
        val client = CreateReminderFragmentArgs.fromBundle(requireArguments()).client

        getViewsState()

        binding.apply {
            selectClientButton.setOnClickListener {
                saveViewsState()
                navController.navigate(R.id.clientListFragment)
            }

            if (client != null) {
                Glide.with(requireContext()).load(client.picture.large).into(selectedClientImage)
                fullNameSelectedClient.text =
                    "${client.fullName.firstName} ${client.fullName.lastName}"
                emailSelectedClient.text = client.email
            }

            dateSwitcher.setOnCheckedChangeListener { _, isChecked ->
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

                    datePickedDialog.setOnCancelListener { dateSwitcher.isChecked = false }
                    datePickedDialog.show()
                } else selectedDateText.text = ""
            }

            timeSwitcher.setOnCheckedChangeListener { _, isChecked ->
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
                    timePicker.addOnNegativeButtonClickListener { timeSwitcher.isChecked = false }
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
                            client?.toClient()!!
                        )

                        if (selectedTimeText.text.isNotEmpty()) {
                            ReminderAlarmManager.createAlarm(requireActivity(), calendar)

                            val clientFullName =
                                "${client.fullName.firstName} ${client.fullName.lastName}"
                            val clientData =
                                ClientDataForNotification(clientFullName, calendar.timeInMillis)

                            ClientMeetingNotification.addClientDataToList(
                                clientData,
                                requireContext()
                            )

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

    private fun saveViewsState() {
        binding.apply {
            viewModel.saveViewsState(
                ViewFieldValues(
                    titleEditText.text.toString(),
                    selectedDateText.text.toString(),
                    selectedTimeText.text.toString(),
                    dateSwitcher.isChecked,
                    timeSwitcher.isChecked,
                    calendar.timeInMillis
                )
            )
        }
    }

    private fun getViewsState() {
        binding.apply {
            val viewFieldValues = viewModel.getViewsState(calendar)

            titleEditText.setText(viewFieldValues.titleText)
            selectedDateText.text = viewFieldValues.dateText
            selectedTimeText.text = viewFieldValues.timeText
            dateSwitcher.isChecked = viewFieldValues.dateSwitcherIsChecked
            timeSwitcher.isChecked = viewFieldValues.timeSwitcherIsChecked
            calendar.timeInMillis = viewFieldValues.calendarTime
        }
    }
}