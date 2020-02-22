package com.github.michalchojnacki.instagramclone.domain.authentication

import com.github.michalchojnacki.instagramclone.common.Result
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Service

@Service
class GenerateTokenForUserUseCase @Autowired constructor(private val authenticationManager: AuthenticationManager,
                                                         private val jwtTokenUseCases: JwtTokenUseCases,
                                                         private val userDetailsService: AppUserDetailsService) {

    operator fun invoke(username: String, password: String): Result<String> {
        try {
            authenticationManager.authenticate(UsernamePasswordAuthenticationToken(username, password))
        } catch (e: BadCredentialsException) {
            return Result.Error(e)
        }
        val userDetails = userDetailsService.loadUserByUsername(username)
        return Result.Success(jwtTokenUseCases.generateToken(userDetails))
    }
}