package com.example.ollamaembeddingtest.core.common.event

import org.springframework.stereotype.Component

@Component
class EventPublisher(
    val eventQueue: EventQueue,
) {
    fun publish(event: AbstractEvent) {
        eventQueue.sink.tryEmitNext(event)
    }
}
