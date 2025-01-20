package com.example.ollamaembeddingtest.core.model.project

import com.example.ollamaembeddingtest.core.model.project.useCase.ProjectCreate
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface ProjectOutPort {
    fun create(createProject: ProjectCreate): Mono<Project>

    fun delete(id: Int): Mono<Void>

    fun existProject(id: Int): Mono<Boolean>

    fun retrieve(): Flux<Project>
}
