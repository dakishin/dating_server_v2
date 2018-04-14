package com.dating.server.test

import com.dating.server.Hello
import org.junit.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.web.reactive.function.client.WebClient
import reactor.test.test

/**
 * Created by dakishin@mail.com  14.04.18.
 */
@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TestRealClient {

    @LocalServerPort
    var port: Int? = null


    // TODO Migrate to WebTestClient when https://youtrack.jetbrains.com/issue/KT-5464 will be fixed
    private var client: WebClient? = null



    @Test
    fun testHello() {
        client = WebClient.create("http://localhost:$port")
        client!!.get().uri("/hello")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .retrieve()
                .bodyToMono(Hello::class.java)
                .test()
                .consumeNextWith {
                    it == Hello()
                }

    }


}