package com.example.ollamaembeddingtest.core.model.embeded.useCase

import com.example.ollamaembeddingtest.core.model.ollama.EmbeddingModel

class EmbededCreate(
    val embededGroupId: Int,
    val model: EmbeddingModel,
    val value: String,
)
