package com.example.learnandroid.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<STATE, INTENT, EFFECT>(
    initialState: STATE
) : ViewModel() {

    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<STATE> = _state

    private val _effects = MutableSharedFlow<EFFECT>()
    val effects: SharedFlow<EFFECT> = _effects

    protected val intents = MutableSharedFlow<INTENT>()

    init {
        viewModelScope.launch {
            intents.collect { intent ->
                handleIntent(intent)
            }
        }
    }

    protected abstract suspend fun handleIntent(intent: INTENT)

    fun sendIntent(intent: INTENT) {
        viewModelScope.launch {
            intents.emit(intent)
        }
    }

    protected suspend fun emitEffect(effect: EFFECT) {
        _effects.emit(effect)
    }

    protected fun updateState(editor: STATE.() -> STATE) {
        _state.value = editor(_state.value)
    }
}
