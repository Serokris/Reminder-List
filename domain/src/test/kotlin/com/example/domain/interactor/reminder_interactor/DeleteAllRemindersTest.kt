package com.example.domain.interactor.reminder_interactor

import com.example.domain.interactors.ReminderInteractor
import com.example.domain.repository.ReminderRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class DeleteAllRemindersTest {

    private val fakeReminderRepository = mock<ReminderRepository>()
    private val reminderInteractor = ReminderInteractor(fakeReminderRepository)

    @Test
    fun `Should be called 'insert' method of the repository`() = runBlocking {
        reminderInteractor.deleteAllReminders()
        verify(fakeReminderRepository).deleteAllReminders()
    }
}