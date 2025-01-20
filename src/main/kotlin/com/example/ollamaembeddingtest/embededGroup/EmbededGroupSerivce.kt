package com.example.ollamaembeddingtest.embededGroup

import com.example.ollamaembeddingtest.common.DomainException
import com.example.ollamaembeddingtest.core.ErrorCode
import com.example.ollamaembeddingtest.core.common.event.EventPublisher
import com.example.ollamaembeddingtest.core.model.embededGroup.EmbededGroup
import com.example.ollamaembeddingtest.core.model.embededGroup.EmbededGroupOutPort
import com.example.ollamaembeddingtest.core.model.embededGroup.event.EmbededGroupDeleteEvent
import com.example.ollamaembeddingtest.core.model.embededGroup.useCase.EmbededGroupCreate
import com.example.ollamaembeddingtest.core.model.embededGroup.useCase.EmbededGroupRetrieve
import com.example.ollamaembeddingtest.embededGroup.repository.EmbededGroupRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class EmbededGroupSerivce(
    private val embededGroupRepository: EmbededGroupRepository,
    private val eventPublisher: EventPublisher,
) : EmbededGroupOutPort {
    override fun create(embededGroupCreate: EmbededGroupCreate): Mono<EmbededGroup> =
        embededGroupRepository
            .existsByProjectIdAndName(
                projectId = embededGroupCreate.projectId,
                name = embededGroupCreate.name,
            ).flatMap { isExist ->
                if (isExist) {
                    Mono.error(DomainException(ErrorCode.DUPLICATED))
                } else {
                    embededGroupRepository.save(
                        EmbededGroup(
                            id = null,
                            projectId = embededGroupCreate.projectId,
                            name = embededGroupCreate.name,
                        ),
                    )
                }
            }

    override fun retrieve(embededGroupRetrieve: EmbededGroupRetrieve): Flux<EmbededGroup> =
        embededGroupRepository.findAllByProjectId(embededGroupRetrieve.projectId)

    override fun delete(id: Int): Mono<Void> =
        embededGroupRepository
            .existsById(id)
            .flatMap { isExist ->
                if (isExist) {
                    embededGroupRepository.deleteById(id)
                } else {
                    Mono.error(DomainException(ErrorCode.NOT_FOUND))
                }
            }.doOnSuccess {
                eventPublisher.publish(
                    EmbededGroupDeleteEvent(
                        embededGroupId = id,
                    ),
                )
            }

    override fun deleteByProjectId(projectId: Int): Mono<Void> =
        embededGroupRepository
            .findAllByProjectId(projectId)
            .flatMap { embededGroup ->
                embededGroup.id?.let { delete(it) } ?: Mono.empty()
            }.then()

    override fun existEmbededGroup(groupId: Int): Mono<Boolean> = embededGroupRepository.existsById(groupId)
}
