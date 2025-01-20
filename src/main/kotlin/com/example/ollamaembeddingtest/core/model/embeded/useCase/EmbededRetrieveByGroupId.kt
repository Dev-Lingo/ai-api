package com.example.ollamaembeddingtest.core.model.embeded.useCase

import com.example.ollamaembeddingtest.core.model.ollama.EmbeddingModel

class EmbededRetrieveByGroupId(
    val embededGroupId: Int,
    val model: EmbeddingModel,
    val offset: Int,
    val limit: Int,
)
