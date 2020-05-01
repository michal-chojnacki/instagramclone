package com.github.michalchojnacki.instagramclone.data.model

import javax.persistence.*

@Entity
@Table(name = "observations")
class RawObservation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null

    @Column(name = "owner_id")
    var ownerId: Long? = null

    @Column(name = "observing_id")
    var observingId: Long? = null

    constructor()

    constructor(ownerId: Long, observingId: Long) {
        this.ownerId = ownerId
        this.observingId = observingId
    }
}