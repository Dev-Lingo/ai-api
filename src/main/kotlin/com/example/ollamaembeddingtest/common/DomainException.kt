package com.example.ollamaembeddingtest.common

import com.example.ollamaembeddingtest.core.ErrorCode

class DomainException(
    val code: ErrorCode,
) : RuntimeException()
