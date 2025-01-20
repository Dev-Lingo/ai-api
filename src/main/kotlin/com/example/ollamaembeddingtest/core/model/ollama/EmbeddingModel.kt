package com.example.ollamaembeddingtest.core.model.ollama

enum class EmbeddingModel(
    val modelName: String,
) {
    NOMIC_EMBED_TEXT("nomic-embed-text"),
    BGE_M3("bge-m3"),
}
