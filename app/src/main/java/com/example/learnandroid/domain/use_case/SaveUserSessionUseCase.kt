package com.example.learnandroid.domain.use_case

import com.example.learnandroid.domain.common.DataStoreKeys
import com.example.learnandroid.domain.model.UserSession
import com.example.learnandroid.domain.repository.DataStoreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SaveUserSessionUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {
    suspend operator fun invoke(userSession: UserSession) {
        withContext(Dispatchers.IO) {
            dataStoreRepository.saveValue(key = DataStoreKeys.EMAIL, value = userSession.email)
            dataStoreRepository.saveValue(key = DataStoreKeys.IS_LOGGED_IN, value = userSession.isLoggedIn)
            dataStoreRepository.saveValue(key = DataStoreKeys.TOKEN, value = userSession.token)
        }
    }
}