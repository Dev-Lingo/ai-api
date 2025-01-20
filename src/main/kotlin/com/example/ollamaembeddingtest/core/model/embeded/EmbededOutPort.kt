package com.example.ollamaembeddingtest.core.model.embeded

import com.example.ollamaembeddingtest.core.model.embeded.useCase.EmbededCreate
import com.example.ollamaembeddingtest.core.model.embeded.useCase.EmbededRetrieveByGroupId
import com.example.ollamaembeddingtest.core.model.embeded.useCase.EmbededSearch
import com.example.ollamaembeddingtest.embeded.repository.response.SimilarEmbeddingResponse
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface EmbededOutPort {
    fun search(embededSearch: EmbededSearch): Flux<SimilarEmbeddingResponse>

    fun findByGroupId(retrieveByGroupId: EmbededRetrieveByGroupId): Flux<Embeded>

    fun createEmbeded(embededCreate: EmbededCreate): Mono<Embeded>

    fun deleteEmbeded(id: Int): Mono<Void>

    fun deleteAllByEmbededGroupId(groupId: Int): Mono<Void>
}
