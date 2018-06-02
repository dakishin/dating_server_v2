package com.dating.server.service

import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

/**
 * Created by dakishin@mail.com  20.04.18.
 */
@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserServiceTest {

    @Autowired
    lateinit var telegramUserService: TelegramUserService

    @Test
    fun testSaveUser() {
        Assert.assertNotNull(telegramUserService)

    }

}