package com.github.michalchojnacki.instagramclone.domain.authentication

import com.github.michalchojnacki.instagramclone.common.Result
import com.github.michalchojnacki.instagramclone.data.UsersRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class AppUserDetailsService @Autowired constructor(private val usersRepository: UsersRepository) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        return when(val result = usersRepository.findUserDetailsByUsername(username)) {
            is Result.Success -> result.data
            is Result.Error -> throw result.exception
        }
    }
}
