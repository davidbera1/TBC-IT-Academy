package com.example.learnandroid.domain.use_case

import com.example.learnandroid.domain.datastore.DataStoreKeys
import com.example.learnandroid.domain.model.UserSession
import com.example.learnandroid.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class ReadUserSessionUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {
    operator fun invoke(): Flow<UserSession> {
        return combine(
            dataStoreRepository.readValue(key = DataStoreKeys.IS_LOGGED_IN, defaultValue = false),
            dataStoreRepository.readValue(key = DataStoreKeys.EMAIL, defaultValue = ""),
            dataStoreRepository.readValue(key = DataStoreKeys.TOKEN, defaultValue = "")
        ) { isLoggedIn, email, token ->
            UserSession(email = email, isLoggedIn = isLoggedIn, token = token)
        }
    }
}