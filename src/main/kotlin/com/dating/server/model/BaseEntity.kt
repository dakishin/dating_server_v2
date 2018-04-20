package com.dating.server.model

import java.io.Serializable
import java.util.*
import javax.persistence.*


/**
 * Created by dakishin@gmail.com 15.04.2016.
 */
@MappedSuperclass
abstract class BaseEntity : Serializable {

    @Id
    @Column(name = "uuid", nullable = false)
    var uuid = UUID.randomUUID().toString()

    @Temporal(TemporalType.TIMESTAMP)
//    @Column(name = "created_date", nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    @Column(name="created_date")
    var createDate = Date()


    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false

        val baseEntity = o as BaseEntity?

        return uuid == baseEntity!!.uuid

    }

    override fun hashCode(): Int {
        return uuid.hashCode()
    }
}
