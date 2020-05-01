package com.github.michalchojnacki.instagramclone.domain.content.model

data class Content(val id: Long,
                   val image: Image,
                   val description: String,
                   val owner: UserDetails,
                   val likesCount : Int,
                   val publicationTimestamp: Long)