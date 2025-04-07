package com.example.learnandroid.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<STATE, EVENT, EFFECT : Any>(
    initialState: STATE
) : ViewModel() {

    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<STATE> = _state

    private val _effects = MutableSharedFlow<EFFECT>()
    val effects: SharedFlow<EFFECT> = _effects

    abstract fun onEvent(event: EVENT)

    protected fun emitEffect(effect: EFFECT) {
        viewModelScope.launch {
            _effects.emit(effect)
        }
    }

    protected fun updateState(editor: STATE.() -> STATE) {
        _state.value = editor(_state.value)
    }
}