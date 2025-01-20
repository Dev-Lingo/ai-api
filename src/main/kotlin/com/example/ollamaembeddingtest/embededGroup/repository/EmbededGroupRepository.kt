package com.example.ollamaembeddingtest.embededGroup.repository

import com.example.ollamaembeddingtest.core.model.embededGroup.EmbededGroup
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
interface EmbededGroupRepository : ReactiveCrudRepository<EmbededGroup, Int> {
    fun findAllByProjectId(projectId: Int): Flux<EmbededGroup>

    fun existsByProjectIdAndName(
        projectId: Int,
        name: String,
    ): Mono<Boolean>
}
