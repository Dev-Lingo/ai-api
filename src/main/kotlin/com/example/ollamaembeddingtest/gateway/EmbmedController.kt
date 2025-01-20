package com.example.ollamaembeddingtest.gateway

import com.example.ollamaembeddingtest.common.DomainException
import com.example.ollamaembeddingtest.core.ErrorCode
import com.example.ollamaembeddingtest.core.model.embeded.Embeded
import com.example.ollamaembeddingtest.core.model.embeded.EmbededOutPort
import com.example.ollamaembeddingtest.core.model.embeded.useCase.EmbededCreate
import com.example.ollamaembeddingtest.core.model.embeded.useCase.EmbededRetrieveByGroupId
import com.example.ollamaembeddingtest.core.model.embeded.useCase.EmbededSearch
import com.example.ollamaembeddingtest.core.model.embededGroup.EmbededGroupOutPort
import com.example.ollamaembeddingtest.core.model.ollama.EmbeddingModel
import com.example.ollamaembeddingtest.core.model.ollama.OllamaOutPort
import com.example.ollamaembeddingtest.embeded.repository.response.SimilarEmbeddingResponse
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toFlux

@RestController
@RequestMapping("/api/embeded")
class EmbmedController(
    private val embededOutPort: EmbededOutPort,
    private val embededGroupOutPort: EmbededGroupOutPort,
    private val ollamaOutPort: OllamaOutPort,
) {
    @GetMapping("/search")
    fun search(request: Mono<EmbededSearch>): Flux<SimilarEmbeddingResponse> =
        request
            .toFlux()
            .flatMap {
                embededOutPort.search(it)
            }

    @GetMapping
    fun retriveByGroupId(request: Mono<EmbededRetrieveByGroupId>): Flux<Embeded> =
        request
            .toFlux()
            .flatMap { embededRetrieveByGroupId ->
                embededGroupOutPort
                    .existEmbededGroup(embededRetrieveByGroupId.embededGroupId)
                    .toFlux()
                    .flatMap { isExist ->
                        if (isExist) {
                            embededOutPort.findByGroupId(embededRetrieveByGroupId)
                        } else {
                            Flux.error(DomainException(ErrorCode.NOT_FOUND))
                        }
                    }
            }

    @PostMapping
    fun createEmbeddingData(
        @RequestBody request: Mono<EmbededCreate>,
    ) = request
        .toFlux()
        .flatMap { embededCreate ->
            embededGroupOutPort
                .existEmbededGroup(embededCreate.embededGroupId)
                .toFlux()
                .flatMap { isExist ->
                    if (isExist) {
                        embededOutPort.createEmbeded(embededCreate)
                    } else {
                        Flux.error(DomainException(ErrorCode.NOT_FOUND))
                    }
                }
        }

    @DeleteMapping
    fun delete(
        @RequestBody id: Mono<Int>,
    ): Mono<Void> = id.flatMap { embededOutPort.deleteEmbeded(it) }

    @GetMapping("/models")
    fun retrieveEmbeddingModel(): Flux<EmbeddingModel> = EmbeddingModel.entries.toTypedArray().toFlux()
}
