package com.example.learnandroid.presentation.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

abstract class BaseViewModel<STATE, EVENT, EFFECT>(
    initialState: STATE
) : ViewModel() {

    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<STATE> = _state

    private val _effects = MutableSharedFlow<EFFECT>()
    val effects: SharedFlow<EFFECT> = _effects

    protected val events = MutableSharedFlow<EVENT>()

    abstract fun onEvent(event: EVENT)

    protected suspend fun emitEffect(effect: EFFECT) {
        _effects.emit(effect)
    }

    protected fun updateState(editor: STATE.() -> STATE) {
        _state.value = editor(_state.value)
    }
}