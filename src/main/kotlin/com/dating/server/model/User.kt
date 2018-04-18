package com.dating.server.model

import com.fasterxml.jackson.annotation.JsonInclude
import org.hibernate.annotations.*
import org.hibernate.annotations.Parameter
import java.util.*
import javax.persistence.*
import javax.persistence.CascadeType
import javax.persistence.Table

/**
 * The Objectify object model for device registrations we are persisting
 */
@javax.persistence.Entity
@Table(name = "user_")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@FilterDef(name = "distance_filter", parameters = arrayOf(ParamDef(name = "latitude", type = "double"), ParamDef(name = "longitude", type = "double"), ParamDef(name = "distance", type = "integer")))
@Filter(name = "distance_filter", condition = "" +
        "(6367 * 2 * atan2(sqrt(\n" +
        "\n" +
        "                pow(sin(((latitude- :latitude) * 0.0174532925199433) / 2.0), 2)\n" +
        "                        + cos(:latitude * 0.0174532925199433) * cos(latitude * 0.0174532925199433) * pow(sin(((longitude - :longitude) * 0.0174532925199433) / 2.0), 2)\n" +
        "\n" +
        "        ), sqrt(1 -\n" +
        "\n" +
        "                pow(sin(((latitude - :latitude) * 0.0174532925199433) / 2.0), 2)\n" +
        "                + cos(:latitude * 0.0174532925199433) * cos(latitude * 0.0174532925199433) * pow(sin(((longitude - :longitude) * 0.0174532925199433) / 2.0), 2)\n" +
        "        ))" +
        "< :distance) and (latitude is not null) and (longitude is not null)" +
        "" +
        "" +
        "" +
        "")
class User : BaseEntity() {

    @Column(name = "is_online")
    var online: Boolean = false

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_online_date", nullable = false, columnDefinition = "TIMESTAMP")
    var lastOnlineDate: Date? = null


    var phone: String? = null
    var name: String? = null

    var email: String? = null

    @Column(name = "is_banned")
    var isBanned: Boolean = false

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = arrayOf(CascadeType.REMOVE))//reverse side
    var avatars: Set<Avatar> = HashSet()

    /**
     * 0 муж
     * 1 женский
     */
    var sex: Int? = null

    val isRussian: Boolean
        get() = RUSSIAN_LOCALE.equals(locale, ignoreCase = true)

    /**
     * 0 в браке не состоял(а)
     * 1 в разводе
     * 2 во вдовстве
     * 3 состою в браке
     * 4 помолвлен(а)
     */
    var marriage: Int? = null

    var birthDate: Long? = null

    var weight: Int? = null
    var height: Int? = null

    var children: Int? = null //0 -no, 1 yes

    /**
     * 0 православное христианство (член Церкви)
     * 1 православное христианство (воцерковляюсь)
     * 2 православное христианство (просто уважаю)
     * 3 католицизм
     * 4 протестантизм
     * 5 ислам
     * 6 прочее
     * 6 на пути к вере
     * 8 атеизм
     */
    var religion: Int? = null
    /**
     * Географические названия ня языке  локали пользователя
     */
    var countryCode: String? = null
    var country: String? = null
    var city: String? = null

    var locale: String? = null

    var latitude: Double? = null
    var longitude: Double? = null

    var password: String? = null

    @Column(name = "telegram_id")
    var telegramId: String? = null


    /**
     * 0 коледж
     * 1 техникум
     * 2 бакалавр
     * 3 магист
     * 4 ВУЗ
     */
    var education: Int? = null

    var pushId: String? = null

    //todo: брать класс для десериализации из параметров анотации
    @Type(type = "com.dating.server.model.JsonStringUserType", parameters = [Parameter(name = "class", value = "com.dating.server.model.ProfileQuestions")])
    @Column(name = "profile_questions")
    var profileQuestions = ProfileQuestions()

    var isSubscription: Boolean = false

    @Column(name = "phone_uuid")
    var phoneUuid: String? = null

    val mainAvatarUuid: String?
        get() {
            for (avatar in avatars) {
                if (avatar.isMain) {
                    return avatar.uuid
                }
            }
            return null
        }

    enum class Sex private constructor(val value: Int) {
        MAN(0),
        WOMAN(1)
    }

    enum class Marriage private constructor(val value: Int) {
        MARRIAGED(3),
        NEVER_MARRIAGED(0),
        DIVORCE(1),
        WIDOW(2)
    }


    companion object {
        var RUSSIAN_LOCALE = "ru"
    }
}