package com.example.remindersaboutmeetingswithclients.di.module

import androidx.lifecycle.ViewModel
import com.example.remindersaboutmeetingswithclients.di.common.ViewModelKey
import com.example.remindersaboutmeetingswithclients.presentation.clientlist.ClientListViewModel
import com.example.remindersaboutmeetingswithclients.presentation.reminder.createreminder.CreateReminderViewModel
import com.example.remindersaboutmeetingswithclients.presentation.reminder.reminderlist.ReminderListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ReminderListViewModel::class)
    fun bindReminderListViewModel(viewModel: ReminderListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CreateReminderViewModel::class)
    fun bindCreateReminderViewModel(viewModel: CreateReminderViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ClientListViewModel::class)
    fun bindClientListViewModel(viewModel: ClientListViewModel): ViewModel
}