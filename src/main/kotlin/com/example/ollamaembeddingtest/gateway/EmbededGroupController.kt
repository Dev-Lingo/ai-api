package com.example.ollamaembeddingtest.gateway

import com.example.ollamaembeddingtest.common.DomainException
import com.example.ollamaembeddingtest.core.ErrorCode
import com.example.ollamaembeddingtest.core.model.embededGroup.EmbededGroup
import com.example.ollamaembeddingtest.core.model.embededGroup.EmbededGroupOutPort
import com.example.ollamaembeddingtest.core.model.embededGroup.useCase.EmbededGroupCreate
import com.example.ollamaembeddingtest.core.model.embededGroup.useCase.EmbededGroupDelete
import com.example.ollamaembeddingtest.core.model.embededGroup.useCase.EmbededGroupRetrieve
import com.example.ollamaembeddingtest.core.model.project.ProjectOutPort
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
@RequestMapping("/api/embeded-group")
class EmbededGroupController(
    private val embededGroupOutPort: EmbededGroupOutPort,
    private val projectOutPort: ProjectOutPort,
) {
    @GetMapping
    fun retrieve(request: Mono<EmbededGroupRetrieve>): Flux<EmbededGroup> =
        request
            .toFlux()
            .flatMap { embededGroupRetrieve ->
                projectOutPort
                    .existProject(embededGroupRetrieve.projectId)
                    .toFlux()
                    .flatMap { isExist ->
                        if (isExist) {
                            embededGroupOutPort.retrieve(embededGroupRetrieve)
                        } else {
                            Flux.error(DomainException(ErrorCode.NOT_FOUND))
                        }
                    }
            }

    @PostMapping
    fun create(
        @RequestBody request: Mono<EmbededGroupCreate>,
    ): Mono<EmbededGroup> =
        request
            .flatMap { embededGroupCreate ->
                projectOutPort
                    .existProject(embededGroupCreate.projectId)
                    .flatMap { isExistProject ->
                        if (isExistProject) {
                            embededGroupOutPort.create(embededGroupCreate)
                        } else {
                            Mono.error(DomainException(ErrorCode.NOT_FOUND))
                        }
                    }
            }

    @DeleteMapping
    fun delete(
        @RequestBody request: Mono<EmbededGroupDelete>,
    ): Mono<Void> = request.flatMap { embededGroupOutPort.delete(it.id) }
}
