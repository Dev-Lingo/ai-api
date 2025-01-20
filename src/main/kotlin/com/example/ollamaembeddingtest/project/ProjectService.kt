package com.example.ollamaembeddingtest.project

import com.example.ollamaembeddingtest.common.DomainException
import com.example.ollamaembeddingtest.core.ErrorCode
import com.example.ollamaembeddingtest.core.common.event.EventPublisher
import com.example.ollamaembeddingtest.core.model.project.Project
import com.example.ollamaembeddingtest.core.model.project.ProjectOutPort
import com.example.ollamaembeddingtest.core.model.project.event.ProjectDeleteEvent
import com.example.ollamaembeddingtest.core.model.project.useCase.ProjectCreate
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class ProjectService(
    private val projectRepository: ProjectRepository,
    private val eventPublisher: EventPublisher,
) : ProjectOutPort {
    override fun create(createProject: ProjectCreate): Mono<Project> =
        projectRepository
            .existsByName(createProject.name)
            .flatMap { isExist ->
                if (isExist) {
                    Mono.error(DomainException(ErrorCode.DUPLICATED))
                } else {
                    projectRepository.save(
                        Project(
                            id = null,
                            name = createProject.name,
                        ),
                    )
                }
            }

    override fun delete(id: Int): Mono<Void> =
        projectRepository
            .existsById(id)
            .flatMap { isExist ->
                if (isExist) {
                    projectRepository.deleteById(id)
                } else {
                    Mono.error(DomainException(ErrorCode.NOT_FOUND))
                }
            }.doOnSuccess {
                eventPublisher.publish(
                    ProjectDeleteEvent(
                        projectId = id,
                    ),
                )
            }

    override fun existProject(id: Int): Mono<Boolean> = projectRepository.existsById(id)

    override fun retrieve(): Flux<Project> = projectRepository.findAll()
}
