package com.example.ollamaembeddingtest.test

import com.example.ollamaembeddingtest.core.common.event.AbstractEventHandler
import com.example.ollamaembeddingtest.core.common.event.EventPublisher
import com.example.ollamaembeddingtest.core.model.embededGroup.event.EmbededGroupDeleteEvent
import jakarta.annotation.PreDestroy
import reactor.core.publisher.Flux
import java.time.Duration

// @Component
class EventTest(
    private val eventPublisher: EventPublisher,
) {
    init {
        println("EventTest init!!")
        Flux
            .interval(Duration.ofMillis(1000))
            .map {
                eventPublisher.publish(EmbededGroupDeleteEvent(-2))
            }.subscribe()
    }
}

// @Component
class TestDomainEventListener : AbstractEventHandler<EmbededGroupDeleteEvent>() {
    override fun handle(event: EmbededGroupDeleteEvent) {
        println(event)
    }
}

// @Component
class Test(
    val list: List<Int> = mutableListOf(1, 2, 3),
) {
    init {
        Flux
            .interval(Duration.ofMillis(500))
            .subscribe {
                println(list)
            }
    }

    @PreDestroy
    fun end() {
    }
}
