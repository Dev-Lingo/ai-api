package com.example.ollamaembeddingtest.embeded.repository

import com.example.ollamaembeddingtest.core.model.embeded.Embeded
import com.example.ollamaembeddingtest.core.model.ollama.EmbeddingModel
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface EmbededRepository : ReactiveCrudRepository<Embeded, Int> {
    @Query(
        """
        SELECT 
            * 
        FROM 
            public.embeded 
        WHERE 
            embeded_group_id = :embededGroupId and
            model = :model
        OFFSET
            :offset
        LIMIT 
            :limit
            """,
    )
    fun findByEmbededGroupId(
        embededGroupId: Int,
        model: EmbeddingModel,
        offset: Int,
        limit: Int,
    ): Flux<Embeded>

    fun deleteAllByEmbededGroupId(embededGroupId: Int): Mono<Void>
}
