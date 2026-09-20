package com.ribani.app.sensors

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

object FallAlertEvents {
    private val _events = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
    val events = _events.asSharedFlow()

    fun notifyPossibleFall() {
        _events.tryEmit(Unit)
    }
}
