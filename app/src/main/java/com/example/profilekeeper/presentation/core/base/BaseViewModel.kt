package com.example.profilekeeper.presentation.core.base

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch


abstract class UiEffect

/**
 * Generic base class for ViewModels using a unidirectional data flow approach.
 * @param State The state type for this ViewModel.
 * @param Event The event type for incoming user actions or triggers.
 */
abstract class BaseViewModel<State, Event>(state: State, event: Event?) : ViewModel() {


    open var state by mutableStateOf(state)

    abstract fun onEvent(event: Event)

    // Flow of one-time events like navigation or toasts (UI side effects)
    private val _effectFlow = MutableSharedFlow<UiEffect>()
    val effectFlow: SharedFlow<UiEffect> = _effectFlow

    protected fun setEffect(builder: () -> UiEffect) {
        viewModelScope.launch {
            val effect = builder()
            _effectFlow.emit(effect)
        }
    }

}
