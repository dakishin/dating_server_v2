package com.dating.server.service

import com.dating.server.dao.PurchaseRepository
import com.dating.server.dao.TelegramUserRepository
import com.dating.server.model.Purchase
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component("purchaseService")
@Transactional
class PurchaseService(val purchaseRepository: PurchaseRepository,val telegramUserRepository: TelegramUserRepository) {


    fun create(telegramId: Long, sku: String, orderId: String): Purchase {
        val user = telegramUserRepository.getByTelegramId(telegramId.toString())!!
        val purchase = Purchase(user, sku, orderId)
        purchaseRepository.save(purchase)
        return purchase
    }


}