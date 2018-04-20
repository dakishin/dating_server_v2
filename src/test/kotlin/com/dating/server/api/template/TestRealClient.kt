package com.dating.server.api.template

import com.dating.server.api.RegisterTelegramUserParam
import com.dating.server.api.Response
import com.dating.server.dao.TelegramUserRepository
import com.dating.server.model.TelegramUser
import com.dating.server.service.TelegramUserService
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.web.reactive.function.client.WebClient
import reactor.test.test

/**
 * Created by dakishin@mail.com  14.04.18.
 */
//@ExtendWith(SpringExtension::class)
@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TestRealClient {

    @LocalServerPort
    var port: Int? = null


    @Autowired
    lateinit var telegramUserService: TelegramUserService

    @Autowired
    lateinit var telegramUserRepository: TelegramUserRepository

    //    // TODO Migrate to WebTestClient when https://youtrack.jetbrains.com/issue/KT-5464 will be fixed
    lateinit var client: WebClient

    @Test
    fun testHello() {
        client = WebClient.create("http://localhost:$port")

        val telegramId = "id"
        val param = RegisterTelegramUserParam(telegramId, "name", "lastname")

        client.post()
                .uri("/api_v3/registerTelegramUser")
                .syncBody(ObjectMapper().writeValueAsString(param))
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .retrieve()
                .bodyToMono(object : ParameterizedTypeReference<Response<TelegramUser>>() {})
                .test()
                .consumeNextWith { result ->
                    //                    Assert.assertEquals(200, result.status.value())
                    val user = telegramUserRepository.getByTelegramId(telegramId)
                    Assert.assertEquals(user, result.result)
                }

    }


}