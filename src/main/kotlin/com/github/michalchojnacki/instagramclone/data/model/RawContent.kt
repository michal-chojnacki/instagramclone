package com.github.michalchojnacki.instagramclone.data.model

import javax.persistence.*

@Entity
@Table(name = "contents")
class RawContent {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) var id: Long? = null
    @Column(nullable = false) var description: String? = null
    @Column(nullable = false) var publicationTimestamp: Long? = null
    @ManyToOne var owner: RawUserDetails? = null
    @Column(unique = true, nullable = false) var imageId: Long? = null

    constructor()

    constructor(description: String, owner: RawUserDetails, imageId: Long, publicationTimestamp: Long) {
        this.description = description
        this.owner = owner
        this.imageId = imageId
        this.publicationTimestamp = publicationTimestamp
    }
}