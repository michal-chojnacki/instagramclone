package com.github.michalchojnacki.instagramclone.domain.content.model

data class UserDetails(val id: Long, val username: String, val avatar: Image, val bio: String, val fullname: String)