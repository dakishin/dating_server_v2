package com.dating.server.service

import com.dating.server.dao.TelegramUserRepository
import com.dating.server.dao.TrebaRepository
import com.dating.server.dao.UserRepository
import com.dating.server.model.Treba
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

/**
 * Created by dakishin@mail.com  21.04.18.
 */
@Component("trebaService")
@Transactional
class TrebaService(val trebaRepository: TrebaRepository,
                   val telegramUserRepository: TelegramUserRepository,
                   val userRepository: UserRepository) {


    fun create(telegramUserId: Int, priestUuid: String, names: List<String>, type: Treba.TrebaType): Treba {
        val mapper = ObjectMapper()
        val user = telegramUserRepository.getByTelegramId(telegramUserId.toString())
        val priest = userRepository.findById(priestUuid).get()
        val treba = Treba(user!!, mapper.writeValueAsString(names), priest, type, Treba.TrebaStatus.WAIT)
        trebaRepository.save(treba)
        return treba
    }

}
