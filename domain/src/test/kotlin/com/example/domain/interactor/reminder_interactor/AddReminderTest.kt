package com.example.domain.interactor.reminder_interactor

import com.example.domain.interactors.ReminderInteractor
import com.example.domain.models.Client
import com.example.domain.models.FullName
import com.example.domain.models.Picture
import com.example.domain.models.ReminderItem
import com.example.domain.repository.ReminderRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class AddReminderTest {

    private val fakeReminderRepository = mock<ReminderRepository>()
    private val reminderInteractor = ReminderInteractor(fakeReminderRepository)
    private val reminderItem = ReminderItem(
        0,
        "Title",
        "Date",
        "Time",
        Client("mail", FullName("Name", "Family"), Picture("picture"))
    )

    @Test
    fun `Should be called 'insert' method of the repository`() = runBlocking {
        reminderInteractor.add(reminderItem)
        verify(fakeReminderRepository).insert(reminderItem)
    }
}