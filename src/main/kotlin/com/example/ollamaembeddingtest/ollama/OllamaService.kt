package com.example.ollamaembeddingtest.ollama

import com.example.ollamaembeddingtest.core.model.ollama.EmbeddingModel
import com.example.ollamaembeddingtest.core.model.ollama.OllamaOutPort
import org.springframework.ai.ollama.api.OllamaApi
import org.springframework.ai.ollama.api.OllamaApi.EmbeddingsRequest
import org.springframework.ai.ollama.api.OllamaApi.EmbeddingsResponse
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers

@Service
class OllamaService(
    private val ollamaApi: OllamaApi,
) : OllamaOutPort {
    //    override fun getEmbededValue(
//        model: EmbeddingModel,
//        value: String,
//    ): Mono<EmbeddingsResponse> {
//        val request = EmbeddingsRequest(model.modelName, value)
//        return Mono.just(ollamaApi.embed(request)).toMono()
//    }

//    override fun getEmbededValue(
//        model: EmbeddingModel,
//        value: String,
//    ): Mono<EmbeddingsResponse> =
//        Mono.defer {
//            val request = EmbeddingsRequest(model.modelName, value)
//            Mono.just(ollamaApi.embed(request))
//        }

//    fun embed(embeddingsRequest: EmbeddingsRequest): Mono<EmbeddingsResponse> =
//        webClient
//            .build()
//            .post()
//            .uri("http://192.168.0.4:11434/api/embed")
//            .bodyValue(embeddingsRequest)
//            .retrieve()
//            .bodyToMono(EmbeddingsResponse::class.java)

    override fun getEmbededValue(
        model: EmbeddingModel,
        value: String,
    ): Mono<EmbeddingsResponse> =
        Mono
            .fromCallable {
                val request = EmbeddingsRequest(model.modelName, value)
                ollamaApi.embed(request) // 동기 호출
            }.subscribeOn(Schedulers.boundedElastic()) // 블로킹 호출을 별도의 스레드에서 실행
}
