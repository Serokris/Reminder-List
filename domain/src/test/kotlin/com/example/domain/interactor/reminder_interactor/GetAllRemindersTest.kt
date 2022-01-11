package com.example.domain.interactor.reminder_interactor

import com.example.domain.interactors.ReminderInteractor
import com.example.domain.models.Client
import com.example.domain.models.FullName
import com.example.domain.models.Picture
import com.example.domain.models.ReminderItem
import com.example.domain.repository.ReminderRepository
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock

class GetAllRemindersTest {

    private val fakeReminderRepository = mock<ReminderRepository>()
    private val reminderInteractor = ReminderInteractor(fakeReminderRepository)
    private val reminderList = listOf(
        ReminderItem(
            0,
            "Title",
            "Date",
            "Time",
            Client("mail", FullName("Name", "Family"), Picture("picture"))
        ),
        ReminderItem(
            1,
            "Title2",
            "Date2",
            "Time2",
            Client("mail2", FullName("Name2", "Family2"), Picture("picture2"))
        )
    )

    @Test
    fun `Should be return list of all reminders`() = runBlocking {
        Mockito.`when`(fakeReminderRepository.getAllReminders()).thenReturn(
            flow { emit(reminderList) }
        )
        val reminderList = reminderInteractor.getAllReminders().single()
        assert(reminderList.isNotEmpty())
    }
}