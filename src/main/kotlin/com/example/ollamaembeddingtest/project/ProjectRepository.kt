package com.example.ollamaembeddingtest.project

import com.example.ollamaembeddingtest.core.model.project.Project
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
interface ProjectRepository : ReactiveCrudRepository<Project, Int> {
    fun existsByName(name: String): Mono<Boolean>

    fun findByName(name: String): Mono<Project>
}
