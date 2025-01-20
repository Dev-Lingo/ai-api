package com.example.ollamaembeddingtest.core.model.embededGroup.event

import com.example.ollamaembeddingtest.core.common.event.AbstractEvent

class EmbededGroupDeleteEvent(
    val embededGroupId: Int,
) : AbstractEvent() {
    override fun toString(): String = "EmbededGroupDeleteEvent(embededGroupId=$embededGroupId)"
}
