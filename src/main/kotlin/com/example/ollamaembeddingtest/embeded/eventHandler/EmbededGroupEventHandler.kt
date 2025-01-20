package com.example.ollamaembeddingtest.embeded.eventHandler

import com.example.ollamaembeddingtest.core.common.event.AbstractEventHandler
import com.example.ollamaembeddingtest.core.model.embeded.EmbededOutPort
import com.example.ollamaembeddingtest.core.model.embededGroup.event.EmbededGroupDeleteEvent
import org.springframework.stereotype.Component

@Component
class EmbededGroupEventHandler(
    private val embededOutPort: EmbededOutPort,
) : AbstractEventHandler<EmbededGroupDeleteEvent>() {
    override fun handle(event: EmbededGroupDeleteEvent) {
        println("EmbededGroup 에 관련된 데이터를 삭제합니다.")
        embededOutPort
            .deleteAllByEmbededGroupId(event.embededGroupId)
            .subscribe()
    }
}
