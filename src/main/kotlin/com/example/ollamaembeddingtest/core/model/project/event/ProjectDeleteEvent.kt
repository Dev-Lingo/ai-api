package com.example.ollamaembeddingtest.core.model.project.event

import com.example.ollamaembeddingtest.core.common.event.AbstractEvent

class ProjectDeleteEvent(
    val projectId: Int,
) : AbstractEvent()
