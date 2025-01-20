package com.example.ollamaembeddingtest.embeded.repository

import com.example.ollamaembeddingtest.core.model.embeded.EmbededLabel
import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.stereotype.Repository

@Repository
interface EmbededLabelRepository : R2dbcRepository<EmbededLabel, Int>
