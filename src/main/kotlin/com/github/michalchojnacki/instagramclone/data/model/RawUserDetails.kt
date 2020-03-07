package com.github.michalchojnacki.instagramclone.data.model

import javax.persistence.*

@Entity
@Table(name = "user_details")
class RawUserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null
    @Column(unique = true, nullable = false)
    var username: String? = null
    @Column(nullable = false)
    var password: String? = null
    @Column(unique = true)
    var avatarId: Long? = null
    @Column
    var bio: String? = null
    @Column
    var name: String? = null

    constructor()

    constructor(username: String, password: String) {
        this.username = username
        this.password = password
    }
}