package com.dating.server.api

import com.dating.server.dao.TelegramUserRepository
import com.dating.server.dao.UserRepository
import com.dating.server.model.TelegramUser
import com.dating.server.model.Treba
import com.dating.server.model.TrebaDTOApi
import com.dating.server.model.User
import com.dating.server.service.TelegramUserService
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.reactive.server.WebTestClient


@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DatingApiTest {

    @Autowired
    lateinit var context: ApplicationContext

    @Autowired
    lateinit var telegramUserService: TelegramUserService

    @Autowired
    lateinit var telegramUserRepository: TelegramUserRepository

    @Autowired
    lateinit var trebaRepository: TelegramUserRepository

    @Autowired
    lateinit var userRepository: UserRepository

    val client: WebTestClient by lazy {
        WebTestClient.bindToApplicationContext(context).build()
    }


    @Test
    fun shouldCreateTelegramUser() {
        val telegramId = "id"
        val param = RegisterTelegramUserParam(telegramId, "name", "lastname")
        val result = client
                .post()
                .uri("/api_v3/registerTelegramUser")
                .syncBody(ObjectMapper().writeValueAsString(param))
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectBody(object : ParameterizedTypeReference<Response<TelegramUser>>() {})
                .returnResult()

        Assert.assertEquals(200, result.status.value())
        val user = telegramUserRepository.getByTelegramId(telegramId)
        Assert.assertEquals(user, result.responseBody?.result)

    }


    @Test
    fun shouldCreateTreba() {
        val telegrmaUser = create()
        val user = createUser()
        val param = CreateTrebaParam(telegrmaUser.telegramId!!.toInt(), arrayListOf("петр"),
                Treba.TrebaType.O_ZDRAVII, user.uuid)


        val trebaDTOApi = client
                .post()
                .uri("/api_v3/createTreba")
                .syncBody(ObjectMapper().writeValueAsString(param))
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectBody(object : ParameterizedTypeReference<Response<TrebaDTOApi>>() {})
                .returnResult().responseBody?.result

        Assert.assertEquals(param.type, trebaDTOApi?.type)
        Assert.assertEquals(param.priestUuid, trebaDTOApi?.priestUuid)
        Assert.assertEquals(user.uuid, trebaDTOApi?.owner)
        Assert.assertEquals(param.names, trebaDTOApi?.names)

    }

    private fun createUser(): User {
        val user = User(tele)
        userRepository.save(user)
        return user
    }

    private fun create(): TelegramUser {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
