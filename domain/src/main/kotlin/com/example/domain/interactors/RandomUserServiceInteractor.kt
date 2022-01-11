package com.example.domain.interactors

import com.example.domain.common.Errors
import com.example.domain.common.Result
import com.example.domain.models.Client
import com.example.domain.repository.RandomUserServiceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RandomUserServiceInteractor @Inject constructor(
    private val randomUserServiceRepository: RandomUserServiceRepository
) {
    fun getRandomClients(count: Int): Flow<Result<List<Client>>> = flow {
        emit(Result.Loading())
        val clientList = randomUserServiceRepository.getRandomClients(count)
        emit(Result.Success(clientList))
    }.catch {
        emit(Result.Error<List<Client>>(Errors.AN_UNEXPECTED_ERROR))
    }
}