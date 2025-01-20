package com.example.ollamaembeddingtest.core.model.embededGroup

import com.example.ollamaembeddingtest.core.model.embededGroup.useCase.EmbededGroupCreate
import com.example.ollamaembeddingtest.core.model.embededGroup.useCase.EmbededGroupRetrieve
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface EmbededGroupOutPort {
    fun create(embededGroupCreate: EmbededGroupCreate): Mono<EmbededGroup>

    fun retrieve(embededGroupRetrieve: EmbededGroupRetrieve): Flux<EmbededGroup>

    fun delete(id: Int): Mono<Void>

    fun deleteByProjectId(projectId: Int): Mono<Void>

    fun existEmbededGroup(groupId: Int): Mono<Boolean>
}
