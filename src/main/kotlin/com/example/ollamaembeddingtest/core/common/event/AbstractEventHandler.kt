package com.example.ollamaembeddingtest.core.common.event

import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.lang.reflect.ParameterizedType

@Component
abstract class AbstractEventHandler<T : AbstractEvent> {
    @Autowired
    private lateinit var eventQueue: EventQueue

    @Suppress("UNCHECKED_CAST")
    private val eventType: Class<T> = (this.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<T>

    protected val runningEvents: MutableSet<T> = mutableSetOf()

    @PostConstruct
    private fun init() {
        eventQueue.sink
            .asFlux()
            .ofType(eventType)
            .doOnNext { runningEvents.add(it) }
            .doOnNext {
                it.state = EventState.RUN
                handle(it)
                it.state = EventState.COMPLETE
            }.doOnNext { runningEvents.remove(it) }
            .subscribe()
    }

    abstract fun handle(event: T)
}
