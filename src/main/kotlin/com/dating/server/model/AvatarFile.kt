package com.dating.server.model

import javax.persistence.*

/**
 * Created by dakishin@mail.com  28.01.17.
 */
@Table(name = "avatar_file")
@javax.persistence.Entity
class AvatarFile : BaseEntity() {

    @OneToOne(cascade = arrayOf(CascadeType.ALL), fetch = FetchType.LAZY)
    @JoinColumn(name = "avatar_uuid")
    var avatar: Avatar? = null

    @Basic(fetch = FetchType.EAGER, optional = false)
    @Column(name = "file_data")
    var file: ByteArray? = null


}
