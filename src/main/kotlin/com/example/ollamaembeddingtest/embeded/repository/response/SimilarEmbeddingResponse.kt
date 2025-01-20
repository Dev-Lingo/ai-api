package com.example.ollamaembeddingtest.embeded.repository.response

class SimilarEmbeddingResponse(
    val id: Long,
    val model: String,
    val value: String,
    val similarity: Double,
) {
    override fun toString(): String = "SimilarEmbeddingResponse(id=$id, model='$model', value='$value', similarity=$similarity)"
}
