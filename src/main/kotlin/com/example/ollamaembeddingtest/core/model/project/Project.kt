package com.example.ollamaembeddingtest.core.model.project

import org.springframework.data.annotation.Id

class Project(
    @Id
    var id: Int?,
    var name: String,
) {
    override fun toString(): String = "Project(id=$id, name='$name')"
}
