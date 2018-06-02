package com.dating.server.model

import com.fasterxml.jackson.annotation.JsonIgnore

import javax.persistence.*


/**
 * Created by dakishin@gmail.com 16.04.2016.
 */
@Table(name = "avatar")
@javax.persistence.Entity
class Avatar : BaseEntity() {

    @JsonIgnore
    @ManyToOne(cascade = arrayOf(CascadeType.ALL), fetch = FetchType.LAZY)
    @JoinColumn(name = "user_uuid")
    var user: User? = null

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    var type = Type.COMMON

    val isMain: Boolean
        get() = type == Type.MAIN

    enum class Type {
        MAIN,
        COMMON,
        SMALL,
        MEDIUM
    }


}
