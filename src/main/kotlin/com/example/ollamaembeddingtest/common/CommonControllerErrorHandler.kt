package com.example.ollamaembeddingtest.common

import com.example.ollamaembeddingtest.core.ErrorCode
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

@RestControllerAdvice
class CommonControllerErrorHandler {
    @ExceptionHandler
    fun resolveErrorResponse(error: DomainException): Mono<ErrorCode> {
        error.printStackTrace()
        return error.code.toMono()
    }

    @ExceptionHandler
    fun resolveErrorResponse(error: RuntimeException): Mono<ErrorCode> {
        error.printStackTrace()
        return error.toMono()
    }
}
