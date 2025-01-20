package com.example.ollamaembeddingtest.core.common.event

import org.springframework.stereotype.Component
import reactor.core.publisher.Sinks

@Component
class EventQueue(
    val sink: Sinks.Many<AbstractEvent> = Sinks.many().multicast().onBackpressureBuffer(),
)
