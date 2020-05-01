package com.github.michalchojnacki.instagramclone.data.model

import javax.persistence.*

@Entity
@Table(name = "likes")
class RawLike {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null

    @Column(name = "user_id")
    var userId: Long? = null

    @Column(name = "content_id")
    var contentId: Long? = null

    @JoinColumn(name = "content_id", insertable = false, updatable = false)
    @ManyToOne(targetEntity = RawContent::class, fetch = FetchType.EAGER)
    var content: RawContent? = null

    constructor()

    constructor(userId: Long, contentId: Long) {
        this.userId = userId
        this.contentId = contentId
    }
}