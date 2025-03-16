package com.example.learnandroid.domain.use_case

import com.example.learnandroid.domain.datastore.DataStoreKeys
import com.example.learnandroid.domain.model.UserSession
import com.example.learnandroid.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ReadUserSessionUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {
    operator fun invoke(): Flow<UserSession> {
        return dataStoreRepository.readValue(key = DataStoreKeys.IS_LOGGED_IN, defaultValue = false)
            .map { isLoggedIn ->
                if (isLoggedIn) {
                    val email = dataStoreRepository.readValue(
                        key = DataStoreKeys.EMAIL,
                        defaultValue = ""
                    ).first()
                    val token = dataStoreRepository.readValue(
                        key = DataStoreKeys.TOKEN,
                        defaultValue = ""
                    ).first()

                    UserSession(
                        email = email,
                        isLoggedIn = isLoggedIn,
                        token = token
                    )
                } else {
                    UserSession(
                        email = "",
                        isLoggedIn = false,
                        token = ""
                    )
                }
            }
    }
}