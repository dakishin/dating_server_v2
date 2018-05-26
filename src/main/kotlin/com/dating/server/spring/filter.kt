package com.dating.server.spring

import org.apache.commons.io.IOUtils
import org.springframework.core.io.buffer.DataBuffer
import org.springframework.core.io.buffer.DefaultDataBufferFactory
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.http.server.reactive.ServerHttpRequestDecorator
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.nio.charset.Charset
import java.util.logging.Level
import java.util.logging.Logger

@Component()
class WebFilterMy : WebFilter {
    val logger = Logger.getLogger("DatingWebFilter")

    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        val isApiPath = exchange.request.path.pathWithinApplication().value().contains("/api_v3/")
        if (isApiPath) {
            return Mono.fromDirect<ServerWebExchange> { publisher ->

                var completed = false

                exchange.request.body
                        .subscribe(
                                {
                                    publisher.onNext(logAndRestore(exchange, it))
                                    publisher.onComplete()
                                    completed = true
                                }
                                ,
                                {
                                    logger.log(Level.SEVERE, it.message, it)
                                    publisher.onNext(exchange)
                                },
                                {
                                    if (!completed){
                                        publisher.onNext(logAndRestore(exchange))
                                        publisher.onComplete()
                                    }

                                })
            }
                    .flatMap {
                        chain.filter(it)
                    }
        } else {
            return chain.filter(exchange)
        }


    }

    private fun logAndRestore(exchange: ServerWebExchange, dataBuffer: DataBuffer? = null): ServerWebExchange {
        val request = exchange.request
        val agent = request.headers.getFirst("User-Agent")
        val path = request.path.pathWithinApplication().value()

        var body: String? = null
        val serverHttpRequestDecorator: ServerHttpRequestDecorator

        if (dataBuffer != null) {
            body = IOUtils.toString(dataBuffer.asInputStream(), "utf8")
            serverHttpRequestDecorator = RequestDecorator(body.toByteArray(Charset.forName("utf8")), request)
        } else {
            serverHttpRequestDecorator = EmptyRequestDecorator(request)
        }

        logger.log(Level.INFO, "Method:$path Header:$agent body: $body")

        return exchange.mutate().request(serverHttpRequestDecorator).build()
    }

}

class RequestDecorator(private val bodyArray: ByteArray, request: ServerHttpRequest) : ServerHttpRequestDecorator(request) {

    override fun getBody(): Flux<DataBuffer> {
        return Flux.just(DefaultDataBufferFactory().wrap(bodyArray))
    }

}

class EmptyRequestDecorator(request: ServerHttpRequest) : ServerHttpRequestDecorator(request) {

    override fun getBody(): Flux<DataBuffer> {
        return Flux.empty()
    }

}