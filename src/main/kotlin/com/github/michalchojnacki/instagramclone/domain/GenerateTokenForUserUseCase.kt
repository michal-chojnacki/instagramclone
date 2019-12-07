package com.github.michalchojnacki.instagramclone.domain

import com.github.michalchojnacki.instagramclone.common.Result
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Service

@Service
class GenerateTokenForUserUseCase {
    @Autowired
    private lateinit var authenticationManager: AuthenticationManager

    @Autowired
    private lateinit var  jwtTokenUseCases: JwtTokenUseCases

    @Autowired
    private lateinit var  userDetailsService: MyUserDetailsService

    operator fun invoke(username: String, password: String) : Result<String> {
        try {
            authenticationManager.authenticate(UsernamePasswordAuthenticationToken(username, password))
        } catch (e: BadCredentialsException) {
            return Result.Error(e)
        }
        val userDetails = userDetailsService.loadUserByUsername(username)
        return Result.Success(jwtTokenUseCases.generateToken(userDetails))
    }
}