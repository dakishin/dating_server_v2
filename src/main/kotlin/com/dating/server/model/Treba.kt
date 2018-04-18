package com.dating.server.model

import java.util.*
import javax.persistence.*

/**
 * Created by dakishin@mail.com  29.10.17.
 */
@javax.persistence.Entity
@Table(name = "treba")
class Treba : BaseEntity() {

    @ManyToOne(cascade = arrayOf(CascadeType.ALL), fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_uuid")
    var owner: TelegramUser? = null

    @Column(name = "names")
    var names: String? = null

    @ManyToOne(cascade = arrayOf(CascadeType.ALL), fetch = FetchType.LAZY)
    @JoinColumn(name = "priest_uuid")
    var priest: User? = null

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    var type: TrebaType? = null

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    var status = TrebaStatus.WAIT

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


    fun toApi(): TrebaDTOApi {
        return TrebaDTOApi(this)
    }

    class TrebaDTOApi constructor(treba: Treba) {

        var owner: String
        var names: List<String> = ArrayList()
        var type: TrebaType? = null
        var priestUuid: String? = null
        var uuid: String
        var createDate: Long? = null
        var status: TrebaStatus

        init {
//            try {
//                this.names = Gson().fromJson(treba.names, object : TypeToken<ArrayList<String>>() {
//
//                }.type)
//            } catch (e: Exception) {
//            }

            this.type = treba.type
            this.owner = treba.owner!!.uuid
            if (treba.priest != null) {
                this.priestUuid = treba.priest!!.uuid
            }
            this.createDate = treba.createDate.time
            this.uuid = treba.uuid
            this.status = treba.status
        }
    }
}

