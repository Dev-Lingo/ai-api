package com.example.ollamaembeddingtest.core.common.event

abstract class AbstractEvent {
    var state: EventState = EventState.READY
}

enum class EventState {
    READY,
    RUN,
    COMPLETE,
}
