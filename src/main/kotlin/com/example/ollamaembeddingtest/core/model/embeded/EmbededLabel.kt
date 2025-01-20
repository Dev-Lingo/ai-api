package com.example.ollamaembeddingtest.core.model.embeded

import org.springframework.data.annotation.Id

class EmbededLabel(
    @Id
    var id: Int?,
    var embededId: Int,
    var value: String,
    var label: String,
)
