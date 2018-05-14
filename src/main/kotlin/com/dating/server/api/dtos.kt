package com.dating.server.api

import com.dating.server.model.Treba
import com.dating.server.model.TrebaDTOApi
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import java.util.ArrayList

data class RegisterTelegramUserParam(val telegramId: String, val firstName: String? = null, val lastName: String? = null)
data class CreateTrebaParam(val telegramUserId: Int, val names: List<String>, val type: Treba.TrebaType, val priestUuid: String)
data class TrebaDTOApi(val owner: String,
                       val names: List<String> = ArrayList(),
                       val type: Treba.TrebaType,
                       val priestUuid: String,
                       val uuid: String,
                       val createDate: Long,
                       val status: Treba.TrebaStatus) {


    companion object {
        fun fromTreba(treba: Treba): TrebaDTOApi {
            var names = arrayListOf<String>()
            try {
                names = ObjectMapper().readValue(treba.names, object : TypeReference<ArrayList<String>>() {})
            } catch (e: Exception) {
            }

            return TrebaDTOApi(type = treba.type, owner = treba.owner.uuid, priestUuid = treba.priest.uuid, createDate = treba.createDate.time,
                    uuid = treba.uuid, status = treba.status, names = names)

        }
    }

}


class SendGeoDataParam(val telegramId: Long, val lat: Double, val lon: Double, val city: String? = null)

data class CreatePurchaseParam(val telegramId: Long, val sku: String, val orderId: String)