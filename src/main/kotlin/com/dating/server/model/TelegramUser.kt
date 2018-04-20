package com.dating.server.model

import javax.persistence.*

/**
 * Created by dakishin@mail.com  26.12.17.
 */
@SqlResultSetMapping(name = "distanceTelegramUserMapping", classes = arrayOf(ConstructorResult(targetClass = TelegramUserDistance::class, columns = arrayOf(ColumnResult(name = "distance"), ColumnResult(name = "telegram_id"), ColumnResult(name = "first_name"), ColumnResult(name = "last_name"), ColumnResult(name = "city"), ColumnResult(name = "latitude"), ColumnResult(name = "longitude")))))
@javax.persistence.Entity
@Table(name = "telegram_user")
open class TelegramUser(
        @Column(name = "telegram_id")
        val telegramId: String? = null,

        @Column(name = "first_name")
        val firstName: String? = null,

        @Column(name = "last_name")
        val lastName: String? = null,

        val city: String? = null,

        val latitude: Double? = null,
        val longitude: Double? = null

) : BaseEntity()