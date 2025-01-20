package com.example.ollamaembeddingtest.gateway

import com.example.ollamaembeddingtest.core.model.project.Project
import com.example.ollamaembeddingtest.core.model.project.ProjectOutPort
import com.example.ollamaembeddingtest.core.model.project.useCase.ProjectCreate
import com.example.ollamaembeddingtest.core.model.project.useCase.ProjectDelete
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/project")
class ProjectController(
    private val projectOutPort: ProjectOutPort,
) {
    @GetMapping
    fun retrieve(): Flux<Project> = projectOutPort.retrieve()

    @PostMapping
    fun create(
        @RequestBody request: Mono<ProjectCreate>,
    ): Mono<Project> = request.flatMap { projectOutPort.create(it) }

    @DeleteMapping
    fun delete(
        @RequestBody id: Mono<ProjectDelete>,
    ): Mono<Void> = id.flatMap { projectOutPort.delete(it.id) }
}
