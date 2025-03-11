package com.example.learnandroid.domain.use_case

import androidx.paging.PagingData
import com.example.learnandroid.domain.model.User
import com.example.learnandroid.domain.repository.UsersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(
    private val usersRepository: UsersRepository
) {
    operator fun invoke(): Flow<PagingData<User>> {
        return usersRepository.getUsers()
    }
}