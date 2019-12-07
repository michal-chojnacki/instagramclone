package com.github.michalchojnacki.instagramclone.domain.authentication

import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class AppUserDetailsService : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        return User("elo", "elo", emptyList())
    }
}