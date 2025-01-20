package com.example.ollamaembeddingtest.embededGroup.eventHandler

import com.example.ollamaembeddingtest.core.common.event.AbstractEventHandler
import com.example.ollamaembeddingtest.core.model.embededGroup.EmbededGroupOutPort
import com.example.ollamaembeddingtest.core.model.project.event.ProjectDeleteEvent
import org.springframework.stereotype.Component

@Component
class ProjectDeleteEventHandler(
    private val embededGroupOutPort: EmbededGroupOutPort,
) : AbstractEventHandler<ProjectDeleteEvent>() {
    override fun handle(event: ProjectDeleteEvent) {
        println("프로젝트에 관련된 EmbededGroup을 삭제합니다")

        embededGroupOutPort
            .deleteByProjectId(
                projectId = event.projectId,
            ).subscribe()
    }
}
