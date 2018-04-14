package com.dating.server.test

import com.dating.server.Api
import com.dating.server.Hello
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.reactive.server.WebTestClient


@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TestMockClient {


    @Test
    fun testHello() {
        val client = WebTestClient.bindToController(Api()).build()

        client.get().uri("/hello")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectBody(Hello::class.java).equals(Hello())


    }
}
