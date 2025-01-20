package com.example.ollamaembeddingtest.core.model.embeded

import com.example.ollamaembeddingtest.core.model.ollama.EmbeddingModel
import org.springframework.data.annotation.Id

class Embeded(
    @Id
    var id: Int?,
    var embededGroupId: Int,
    var value: String,
    var model: EmbeddingModel,
    var embedData: List<Float> = emptyList(),
) {
    override fun toString(): String = "Embeded(id=$id, embededGroupId=$embededGroupId, value='$value', model=$model, embedData=$embedData)"
}
