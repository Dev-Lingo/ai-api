package com.example.ollamaembeddingtest.core.conig

import com.example.ollamaembeddingtest.core.model.embeded.Embeded
import com.example.ollamaembeddingtest.core.model.ollama.EmbeddingModel
import io.r2dbc.spi.Row
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions
import org.springframework.data.r2dbc.dialect.PostgresDialect
import org.springframework.data.r2dbc.dialect.R2dbcDialect

@Configuration
class R2dbcCustomConverterConfig {
    @Bean
    fun r2dbcCustomConversions(dialect: R2dbcDialect): R2dbcCustomConversions =
        R2dbcCustomConversions.of(
            dialect,
            listOf(
                StringToFloatListConverter(),
            ),
        )

    @Bean
    fun r2dbcDialect(): R2dbcDialect = PostgresDialect.INSTANCE
}

@ReadingConverter
class StringToFloatListConverter : Converter<Row, Embeded> {
    override fun convert(source: Row): Embeded {
        val embedDataRow = source.get("embed_data", String::class.java)

        val embedData =
            embedDataRow
                ?.removeSurrounding("[", "]")
                ?.split(",")
                ?.map { it.toFloat() } ?: emptyList()

        val embedModelRow = source.get("model", String::class.java) ?: throw RuntimeException()
        val embedModel = EmbeddingModel.valueOf(embedModelRow)

        return Embeded(
            source.get("id", Int::class.java),
            source.get("embeded_group_id", Int::class.java) ?: throw RuntimeException(),
            source.get("value", String::class.java) ?: throw RuntimeException(),
            embedModel,
            embedData,
        )
    }
}
