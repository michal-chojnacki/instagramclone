package com.github.michalchojnacki.instagramclone.domain.content.model

data class Content(val image: Image, val description : String, val owner: User, val publicationTimestamp: Long)