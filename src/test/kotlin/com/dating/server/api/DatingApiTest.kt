package com.dating.server.api

import com.dating.server.BaseTest
import com.dating.server.dao.TelegramUserRepository
import com.dating.server.dao.TrebaRepository
import com.dating.server.dao.UserRepository
import com.dating.server.model.*
import com.dating.server.model.TrebaDTOApi
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


class DatingApiTest : BaseTest() {


    @Test
    fun shouldCreateTelegramUser() {
        val telegramId = randomStr()
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
        val telegrmaUser = createTelegramUser()
        val priest = createUser()
        val param = CreateTrebaParam(telegrmaUser.telegramId!!.toInt(), arrayListOf("петр"),
                Treba.TrebaType.O_ZDRAVII, priest.uuid)


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
        Assert.assertEquals(telegrmaUser.uuid, trebaDTOApi?.owner)
        Assert.assertEquals(param.names, trebaDTOApi?.names)

    }

    @Test
    fun shouldGetTrebas() {
        val treba1 = createTreba()

        val trebaFromDb = trebaRepository.getByOwner(treba1.owner.telegramId!!)

        Assert.assertNotNull(trebaFromDb[0])

        val trebas: List<TrebaDTOApi> =
                client
                        .post()
                        .uri("/api_v3/getTrebas/${treba1.owner.telegramId}")
                        .syncBody("")
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .exchange()
                        .expectBody(object : ParameterizedTypeReference<Response<List<TrebaDTOApi>>>() {})
                        .returnResult().responseBody?.result!!

        val trebaResult1 = trebas[0]

        Assert.assertEquals(trebaResult1.type, treba1.type)
        Assert.assertEquals(trebaResult1.priestUuid, treba1.priest.uuid)
        Assert.assertEquals(trebaResult1.uuid, treba1.uuid)
    }

    @Test
    fun shouldSendGeoData() {
        val telegramUser = createTelegramUser()
        val param = SendGeoDataParam(telegramUser.telegramId!!.toLong(), random().toDouble(), random().toDouble(), randomStr())


        val trebaDTOApi = client
                .post()
                .uri("/api_v3/sendGeoDataV2")
                .syncBody(ObjectMapper().writeValueAsString(param))
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectBody(object : ParameterizedTypeReference<Response<Unit>>() {})
                .returnResult().responseBody?.result

        val newTelegramUser = telegramUserRepository.getByTelegramId(telegramUser.telegramId!!)!!
        Assert.assertEquals(newTelegramUser.latitude, param.lat)
        Assert.assertEquals(newTelegramUser.longitude, param.lon)
        Assert.assertEquals(newTelegramUser.city, param.city)


    }

    @Test
    fun shouldSearch() {
        val userToFind = createTelegramUser(55.752411, 37.385560)
        val telegramUser = createTelegramUser(55.775639, 37.885438)

        val users: List<TelegramUserDistance> =
                client
                        .get()
                        .uri("/api_v3/search/${telegramUser.telegramId}")
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .exchange()
                        .expectBody(object : ParameterizedTypeReference<Response<List<TelegramUserDistance>>>() {})
                        .returnResult().responseBody?.result!!

        val userBack = users[0]
        Assert.assertEquals(userBack.telegramId, userToFind.telegramId)
    }


    @Test
    fun shouldCreatePurchase() {
        val telegrmaUser = createTelegramUser()
        val param = CreatePurchaseParam(telegrmaUser.telegramId!!.toLong(), "sku", "orderId")


        val purchase = client
                .post()
                .uri("/api_v3/createPurchase")
                .syncBody(ObjectMapper().writeValueAsString(param))
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectBody(object : ParameterizedTypeReference<Response<Purchase>>() {})
                .returnResult().responseBody?.result!!

        Assert.assertEquals(param.telegramId, purchase.owner!!.telegramId!!.toLong())
        Assert.assertEquals(param.sku, purchase.sku)
        Assert.assertEquals(param.orderId, purchase.orderId)

    }

}
