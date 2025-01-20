package com.example.ollamaembeddingtest.embeded.repository

import com.example.ollamaembeddingtest.core.model.ollama.EmbeddingModel
import com.example.ollamaembeddingtest.embeded.repository.response.SimilarEmbeddingResponse
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
class EmbededQueryRepository(
    private val databaseClient: DatabaseClient,
) {
    fun findSimilarEmbeddings(
        model: EmbeddingModel,
        vector: List<Float>,
        limit: Int = 10,
    ): Flux<SimilarEmbeddingResponse> {
        // 벡터를 문자열로 변환 (PostgreSQL ARRAY 형식)
        val vectorString = vector.joinToString(prefix = "[", postfix = "]")

        // SQL 쿼리 작성
        val sql =
            """
            SELECT 
                id, model, value, embed_data <=> '$vectorString' AS similarity
            FROM 
                public.embeded
            WHERE 
                model = '$model'
            ORDER BY 
                similarity ASC
            LIMIT $limit
            """.trimIndent()

        // DatabaseClient를 사용하여 쿼리 실행
        return databaseClient
            .sql(sql)
            .map { row ->
                SimilarEmbeddingResponse(
                    id = row.get("id", Long::class.java)!!,
                    model = row.get("model", String::class.java)!!,
                    value = row.get("value", String::class.java)!!,
                    similarity = row.get("similarity", Double::class.java)!!,
                )
            }.all()
    }
}
