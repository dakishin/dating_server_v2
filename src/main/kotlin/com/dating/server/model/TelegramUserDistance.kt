package com.dating.server.model

import javax.persistence.Entity


class TelegramUserDistance(var distance: Int?, telegramId: String, firstName: String, lastName: String, city: String, latitude: Double?, longitude: Double?) : TelegramUser() {

    init {
        this.telegramId = telegramId
        this.firstName = firstName
        this.lastName = lastName
        this.city = city
        this.latitude = latitude
        this.longitude = longitude
    }
}
