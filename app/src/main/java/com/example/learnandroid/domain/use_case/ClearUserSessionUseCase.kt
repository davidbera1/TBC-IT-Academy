package com.example.learnandroid.domain.use_case

import com.example.learnandroid.domain.common.DataStoreKeys
import com.example.learnandroid.domain.repository.DataStoreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ClearUserSessionUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {
    suspend operator fun invoke() {
        withContext(Dispatchers.IO) {
            dataStoreRepository.removeByKey(DataStoreKeys.EMAIL)
            dataStoreRepository.removeByKey(DataStoreKeys.IS_LOGGED_IN)
            dataStoreRepository.removeByKey(DataStoreKeys.TOKEN)
        }
    }
}