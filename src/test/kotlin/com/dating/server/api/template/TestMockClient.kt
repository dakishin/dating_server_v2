package com.dating.server.api.template

import com.dating.server.api.DatingApi
import com.dating.server.service.UserService
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.reactive.server.WebTestClient


@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TestMockClient {

    @Autowired
    lateinit var userService: UserService

    @Test
    fun testHello() {
        val client = WebTestClient.bindToController(DatingApi(userService)).build()

        val createUserParam = Hello()
        client.post()
                .uri("/create")
                .syncBody(createUserParam)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectBody(Hello::class.java)
                .equals(createUserParam)


    }
}

data class Hello(val name: String = "name")