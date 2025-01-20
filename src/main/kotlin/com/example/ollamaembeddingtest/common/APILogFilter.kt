package com.example.ollamaembeddingtest.common

import mu.KotlinLogging
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono

private val logger = KotlinLogging.logger {}

@Component
class APILogFilter : WebFilter {
    override fun filter(
        exchange: ServerWebExchange,
        chain: WebFilterChain,
    ): Mono<Void> {
        val standardTime = System.currentTimeMillis()
        return chain.filter(exchange).doAfterTerminate {
            val nowTime = System.currentTimeMillis()
            logger.info { "Request-PATH : ${exchange.request.path} - ${nowTime - standardTime} ms" }
        }
    }
}
