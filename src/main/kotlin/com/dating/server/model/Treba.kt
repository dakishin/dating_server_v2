package com.dating.server.model

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import java.util.*
import javax.persistence.*


/**
 * Created by dakishin@mail.com  29.10.17.
 */
@javax.persistence.Entity
@Table(name = "treba")
class Treba(
        @ManyToOne(cascade = arrayOf(CascadeType.ALL), fetch = FetchType.LAZY)
        @JoinColumn(name = "owner_uuid")
        var owner: TelegramUser,

        @Column(name = "names")
        val names: String,

        @ManyToOne(cascade = arrayOf(CascadeType.ALL), fetch = FetchType.LAZY)
        @JoinColumn(name = "priest_uuid")
        val priest: User? = null,

        @Column(name = "type")
        @Enumerated(EnumType.STRING)
        val type: TrebaType,

        @Column(name = "status")
        @Enumerated(EnumType.STRING)
        var status: TrebaStatus = TrebaStatus.WAIT

) : BaseEntity() {


    fun priestName(): String {
        val name = priest?.name
        if (name != null) {
            return name
        } else {
            return ""
        }
    }

    enum class TrebaType {
        O_ZDRAVII,
        OB_UPOKOY,
        MOLEBEN_PETRU_I_FEVRONII,

        ALKO,
        K_RODAM,
        PERED_DOBR_DELOM,

        BLAGODARSTVENNIY,
        SOROCOUST_ZDR,
        SOROCOUST_UPOKOY
    }

    enum class TrebaStatus {
        WAIT, TAKEN
    }



}

data class TrebaDTOApi(
        val owner: String,
        val names: List<String>,
        val type: Treba.TrebaType,
        val priestUuid: String?,
        val uuid: String,
        val createDate: Long,
        val status: Treba.TrebaStatus
) {

    companion object {

        fun fromTreba(treba: Treba): TrebaDTOApi {
            val mapper = ObjectMapper()
            var names = arrayListOf<String>()

            try {
                names = mapper.readValue(treba.names, object : TypeReference<ArrayList<String>>() {})
            } catch (e: Exception) {
            }

            return TrebaDTOApi(type = treba.type, owner = treba.owner.uuid,
                    createDate = treba.createDate.time, priestUuid = treba.priest?.uuid
                    , names = names, status = treba.status, uuid = treba.uuid)
        }
    }


}

