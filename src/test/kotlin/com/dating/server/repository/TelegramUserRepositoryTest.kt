package com.dating.server.repository

import com.dating.server.BaseTest
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner


class TelegramUserRepositoryTest : BaseTest() {


    @Test
    fun shouldSearch(){
        createTelegramUser()
        val telegramUser = createTelegramUser()

        val user = telegramUserRepository.searchNear(telegramUser.telegramId!!)

    }



}