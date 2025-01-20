package com.example.ollamaembeddingtest.core.model.embededGroup

import org.springframework.data.annotation.Id

class EmbededGroup(
    @Id
    var id: Int?,
    var projectId: Int,
    var name: String,
)
