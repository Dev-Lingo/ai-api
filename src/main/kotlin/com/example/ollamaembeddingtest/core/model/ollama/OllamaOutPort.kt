package com.example.ollamaembeddingtest.core.model.ollama

import org.springframework.ai.ollama.api.OllamaApi.EmbeddingsResponse
import reactor.core.publisher.Mono

interface OllamaOutPort {
    fun getEmbededValue(
        model: EmbeddingModel,
        value: String,
    ): Mono<EmbeddingsResponse>

//    fun getEmbededValueTest(
//        model: EmbeddingModel,
//        value: String,
//    ): Mono<EmbeddingsResponse>
}
