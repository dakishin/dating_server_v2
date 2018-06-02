package com.dating.server.model

import javax.persistence.*

/**
 * Created by dakishin@mail.com  26.12.17.
 */
@SqlResultSetMapping(name = "distanceTelegramUserMapping",
        classes = [(
                ConstructorResult(
                        targetClass = TelegramUserDistance::class,
                        columns = arrayOf(
                                ColumnResult(name = "distance"),
                                ColumnResult(name = "telegram_id"), ColumnResult(name = "first_name"),
                                ColumnResult(name = "last_name"), ColumnResult(name = "city"),
                                ColumnResult(name = "latitude"), ColumnResult(name = "longitude")
                        )
                )
                )
        ])
@NamedNativeQueries(
        NamedNativeQuery(name = "TelegramUser_searchNear", query = "SELECT " +
                " calc_distance(user_near.latitude, user_near.longitude,user_owner.latitude,user_owner.longitude) AS distance, " +
                " user_near.telegram_id as telegram_id,  user_near.first_name as first_name, user_near.last_name as last_name, user_near.city as city," +
                "  user_near.latitude as latitude, user_near.longitude as longitude " +
                "FROM " +
                " telegram_user AS user_near ,telegram_user AS user_owner" +
                " WHERE" +
                " (user_near.latitude IS NOT NULL) AND (user_near.longitude IS NOT NULL) AND user_owner.telegram_id=:telegramId AND (user_near.telegram_id<>user_owner.telegram_id)" +
                " ORDER BY " +
                " distance ASC", resultSetMapping = "distanceTelegramUserMapping")
)


@javax.persistence.Entity
@Table(name = "telegram_user")
open class TelegramUser(

        @Column(name = "telegram_id", unique = true)
        var telegramId: String? = null,

        @Column(name = "first_name")
        var firstName: String? = null,

        @Column(name = "last_name")
        var lastName: String? = null,

        var city: String? = null,

        var latitude: Double? = null,
        var longitude: Double? = null

) : BaseEntity()