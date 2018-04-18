package com.dating.server.model

import javax.persistence.*

/**
 * Created by dakishin@mail.com  16.02.18.
 */
@javax.persistence.Entity
@Table(name = "purchase")
data class Purchase(@ManyToOne(cascade = arrayOf(CascadeType.ALL), fetch = FetchType.LAZY)
                    @JoinColumn(name = "telegram_user_uuid") var owner: TelegramUser,
                    var sku: String, @Column(name = "order_id") var orderId: String) : BaseEntity() {

}
