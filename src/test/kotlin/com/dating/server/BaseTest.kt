package com.dating.server

import com.dating.server.dao.TelegramUserRepository
import com.dating.server.dao.TrebaRepository
import com.dating.server.dao.UserRepository
import com.dating.server.model.TelegramUser
import com.dating.server.model.Treba
import com.dating.server.model.User
import com.dating.server.service.TelegramUserService
import org.junit.Before
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.reactive.server.WebTestClient


@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
abstract class BaseTest {

    @Autowired
    lateinit var context: ApplicationContext

    @Autowired
    lateinit var telegramUserService: TelegramUserService

    @Autowired
    lateinit var telegramUserRepository: TelegramUserRepository

    @Autowired
    lateinit var trebaRepository: TrebaRepository

    @Autowired
    lateinit var userRepository: UserRepository


    val client: WebTestClient by lazy {
        WebTestClient.bindToApplicationContext(context).build()
    }

    @Before
    fun setUp() {
        telegramUserRepository.deleteAll()
        userRepository.deleteAll()
        trebaRepository.deleteAll()

    }

    fun createTreba(): Treba {
        val owner = createTelegramUser();
        val priest = createUser()
        return trebaRepository.save(Treba(names = "", owner = owner, priest = priest, type = Treba.TrebaType.ALKO, status = Treba.TrebaStatus.WAIT))
    }

    fun createUser(): User {
        val user = User(pushId = "xxx", email = randomStr())
        userRepository.save(user)
        return user
    }

    fun createTelegramUser(lat: Double = 56.0, lon: Double = 34.0): TelegramUser {
        val user = TelegramUser(telegramId = randomStr(), firstName = randomStr(), lastName = "", city = "Moscow", longitude = lon, latitude = lat)
        telegramUserRepository.save(user)
        return user

    }

    fun randomStr(): String {
        return ((Math.round(Math.random() * 1000000))).toString()
    }

    fun random(): Long {
        return Math.round(Math.random() * 1000000)
    }

}