package com.dating.server.model

import javax.persistence.*

/**
 * Created by dakishin@mail.com  16.02.18.
 */
@javax.persistence.Entity
@Table(name = "purchase")
class Purchase(
        @ManyToOne(cascade = arrayOf(CascadeType.ALL), fetch = FetchType.LAZY)
        @JoinColumn(name = "telegram_user_uuid")
        val owner: TelegramUser? = null,

        val sku: String? = null,

        @Column(name = "order_id")
        val orderId: String? = null) : BaseEntity() {

}
