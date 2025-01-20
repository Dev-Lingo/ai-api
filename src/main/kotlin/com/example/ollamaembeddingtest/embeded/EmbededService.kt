package com.example.ollamaembeddingtest.embeded

import com.example.ollamaembeddingtest.common.DomainException
import com.example.ollamaembeddingtest.core.ErrorCode
import com.example.ollamaembeddingtest.core.model.embeded.Embeded
import com.example.ollamaembeddingtest.core.model.embeded.EmbededOutPort
import com.example.ollamaembeddingtest.core.model.embeded.useCase.EmbededCreate
import com.example.ollamaembeddingtest.core.model.embeded.useCase.EmbededRetrieveByGroupId
import com.example.ollamaembeddingtest.core.model.embeded.useCase.EmbededSearch
import com.example.ollamaembeddingtest.core.model.ollama.OllamaOutPort
import com.example.ollamaembeddingtest.embeded.repository.EmbededLabelRepository
import com.example.ollamaembeddingtest.embeded.repository.EmbededQueryRepository
import com.example.ollamaembeddingtest.embeded.repository.EmbededRepository
import com.example.ollamaembeddingtest.embeded.repository.response.SimilarEmbeddingResponse
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toFlux

@Service
class EmbededService(
    private val ollamaOutPort: OllamaOutPort,
    private val embededRepository: EmbededRepository,
    private val embededQueryRepository: EmbededQueryRepository,
    private val embededLabelRepository: EmbededLabelRepository,
) : EmbededOutPort {
    override fun search(embededSearch: EmbededSearch): Flux<SimilarEmbeddingResponse> =
        ollamaOutPort
            .getEmbededValue(embededSearch.model, embededSearch.search)
            .toFlux()
            .flatMap { embeddedResponse ->
                embededQueryRepository.findSimilarEmbeddings(
                    model = embededSearch.model,
                    vector = embeddedResponse.embeddings.flatMap { it.toList() },
                    limit = embededSearch.limit,
                )
            }

    override fun findByGroupId(retrieveByGroupId: EmbededRetrieveByGroupId): Flux<Embeded> =
        embededRepository.findByEmbededGroupId(
            embededGroupId = retrieveByGroupId.embededGroupId,
            model = retrieveByGroupId.model,
            offset = retrieveByGroupId.offset,
            limit = retrieveByGroupId.limit,
        )

    override fun createEmbeded(embededCreate: EmbededCreate): Mono<Embeded> =
        ollamaOutPort
            .getEmbededValue(
                model = embededCreate.model,
                value = embededCreate.value,
            ).flatMap { embeddingResponse ->
                embededRepository.save(
                    Embeded(
                        embededGroupId = embededCreate.embededGroupId,
                        model = embededCreate.model,
                        value = embededCreate.value,
                        embedData = embeddingResponse.embeddings.flatMap { it.toList() },
                        id = null,
                    ),
                )
            }

    override fun deleteEmbeded(id: Int): Mono<Void> =
        embededRepository
            .existsById(id)
            .flatMap { isExist ->
                if (isExist) {
                    embededRepository.deleteById(id)
                } else {
                    Mono.error(DomainException(ErrorCode.NOT_FOUND))
                }
            }

    override fun deleteAllByEmbededGroupId(groupId: Int): Mono<Void> = embededRepository.deleteAllByEmbededGroupId(groupId)
}
