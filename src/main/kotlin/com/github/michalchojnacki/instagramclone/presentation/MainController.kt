package com.github.michalchojnacki.instagramclone.presentation

import com.github.michalchojnacki.instagramclone.common.Result
import com.github.michalchojnacki.instagramclone.common.exhaustive
import com.github.michalchojnacki.instagramclone.presentation.model.AuthenticationRequest
import com.github.michalchojnacki.instagramclone.presentation.model.AuthenticationResponse
import com.github.michalchojnacki.instagramclone.domain.GenerateTokenForUserUseCase
import com.github.michalchojnacki.instagramclone.domain.RegisterNewUserUseCase
import com.github.michalchojnacki.instagramclone.domain.UnexpectedException
import com.github.michalchojnacki.instagramclone.domain.model.UserAlreadyExistsException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.web.bind.annotation.*

@RestController
class MainController {
    @Autowired
    private lateinit var generateTokenForUser: GenerateTokenForUserUseCase

    @Autowired
    private lateinit var registerNewUser: RegisterNewUserUseCase

    @GetMapping("/")
    fun elo() : String {
        return "elo"
    }

    @PostMapping("/register")
    fun registerUser(@RequestBody authenticationRequest: AuthenticationRequest): ResponseEntity<AuthenticationResponse>? {
        when(val result = registerNewUser(authenticationRequest.username, authenticationRequest.password)) {
            is Result.Success -> Unit
            is Result.Error -> when(val e = result.exception) {
                is UserAlreadyExistsException -> throw Exception("Oops! Something unexpected happened", e)
                else -> throw UnexpectedException(e)
            }
        }.exhaustive
        return when(val result = generateTokenForUser(authenticationRequest.username, authenticationRequest.password)) {
            is Result.Success -> ResponseEntity.ok(AuthenticationResponse(result.data))
            is Result.Error -> when(val e = result.exception) {
                else -> throw UnexpectedException(e)
            }
        }
    }

    @PostMapping("/authenticate")
    fun createAuthenticationToken(@RequestBody authenticationRequest: AuthenticationRequest): ResponseEntity<AuthenticationResponse>? {
        return when(val result = generateTokenForUser(authenticationRequest.username, authenticationRequest.password)) {
            is Result.Success -> ResponseEntity.ok(AuthenticationResponse(result.data))
            is Result.Error -> when(val e = result.exception) {
                is BadCredentialsException -> throw Exception("Incorrect username or password", e)
                else -> throw UnexpectedException(e)
            }
        }
    }
}