package com.example.learnandroid.presentation.screens.transfer

import com.example.learnandroid.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TransferViewModel @Inject constructor() :
    BaseViewModel<TransferState, TransferEvent, TransferEffect>(TransferState()) {

    override fun onEvent(event: TransferEvent) {

    }
}