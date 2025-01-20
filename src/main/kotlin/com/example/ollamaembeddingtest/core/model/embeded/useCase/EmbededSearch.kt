package com.example.ollamaembeddingtest.core.model.embeded.useCase

import com.example.ollamaembeddingtest.core.model.ollama.EmbeddingModel

class EmbededSearch(
    val model: EmbeddingModel,
    val search: String,
    val limit: Int,
)
