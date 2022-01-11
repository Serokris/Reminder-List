package com.example.domain.interactor.random_user_service_interactor

import com.example.domain.common.Errors
import com.example.domain.common.Result
import com.example.domain.interactors.RandomUserServiceInteractor
import com.example.domain.models.Client
import com.example.domain.models.FullName
import com.example.domain.models.Picture
import com.example.domain.repository.RandomUserServiceRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock

class GetRandomClientsTest {

    private val fakeRandomUserRepository = mock<RandomUserServiceRepository>()
    private val randomUserServiceInteractor = RandomUserServiceInteractor(fakeRandomUserRepository)
    private val clientsAmount = 15
    private val clientsList = listOf(
        Client("some1@mail.ru", FullName("Name1", "Family1"), Picture("picture1")),
        Client("some2@mail.ru", FullName("Name2", "Family2"), Picture("picture2")),
        Client("some3@mail.ru", FullName("Name3", "Family3"), Picture("picture3"))
    )

    @Test
    fun `The first value emitted should be is 'Loading'`() = runBlocking {
        Mockito.`when`(fakeRandomUserRepository.getRandomClients(clientsAmount))
            .thenReturn(clientsList)
        val result = randomUserServiceInteractor.getRandomClients(clientsAmount).first()
        assert(result is Result.Loading)
    }

    @Test
    fun `The last value emitted should be is 'Success'`() = runBlocking {
        Mockito.`when`(fakeRandomUserRepository.getRandomClients(clientsAmount))
            .thenReturn(clientsList)
        val result = randomUserServiceInteractor.getRandomClients(clientsAmount).last()
        assert(result is Result.Success && result.data == clientsList)
    }

    @Test
    fun `Expected catching Exception`() = runBlocking {
        Mockito.`when`(fakeRandomUserRepository.getRandomClients(clientsAmount)).then {
            throw Exception()
        }
        val result = randomUserServiceInteractor.getRandomClients(clientsAmount).last()
        assert(result is Result.Error && result.message == Errors.AN_UNEXPECTED_ERROR)
    }
}