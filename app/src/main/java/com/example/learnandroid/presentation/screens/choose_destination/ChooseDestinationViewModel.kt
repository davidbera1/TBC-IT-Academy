package com.example.learnandroid.presentation.screens.choose_destination

import androidx.lifecycle.ViewModel
import com.example.learnandroid.data.remote.AccountStatusService
import com.example.learnandroid.domain.use_case.ValidateAccountUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChooseDestinationViewModel @Inject constructor(
    private val validateAccountUseCase: ValidateAccountUseCase,
    private val checkAccountStatusService: AccountStatusService
) : ViewModel() {

    fun validateAccount(accountNumber: String): Boolean {
        return validateAccountUseCase(accountNumber)
    }

    suspend fun checkAccountStatus(accountNumber: String): Boolean {
        val response = checkAccountStatusService.checkAccountStatus()
        return response.status == "Success"
    }
}