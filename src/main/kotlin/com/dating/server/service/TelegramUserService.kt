package com.dating.server.service

import com.dating.server.dao.TelegramUserRepository
import com.dating.server.model.TelegramUser
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

/**
 * Created by dakishin@mail.com  20.04.18.
 */
@Component("telegramUserService")
@Transactional
class TelegramUserService(val telegramUserRepository: TelegramUserRepository) {

    fun create(telegramId: String, firstName: String?, lastName: String?): TelegramUser {
        val telegramUser = TelegramUser(telegramId = telegramId, firstName = firstName, lastName = lastName)
        telegramUserRepository.save(telegramUser)
        return telegramUser
    }

}
