package com.github.michalchojnacki.instagramclone.presentation.profile

import com.github.michalchojnacki.instagramclone.common.Result
import com.github.michalchojnacki.instagramclone.domain.common.UnexpectedException
import com.github.michalchojnacki.instagramclone.domain.content.model.UserDetails
import com.github.michalchojnacki.instagramclone.domain.profile.GetObservingStatusUseCase
import com.github.michalchojnacki.instagramclone.domain.profile.GetUserDetailsUseCase
import com.github.michalchojnacki.instagramclone.presentation.profile.model.ObservingStatus
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.security.Principal

@RestController
class ProfileController @Autowired constructor(private val getUserDetails: GetUserDetailsUseCase,
                                               private val getObservingStatus: GetObservingStatusUseCase) {
    @GetMapping("/user")
    fun provideUserDetails(principal: Principal): ResponseEntity<UserDetails> {
        return when (val result = getUserDetails(principal.name)) {
            is Result.Success -> ResponseEntity.ok(result.data)
            is Result.Error -> throw UnexpectedException(result.exception)
        }
    }

    @GetMapping("/observing")
    fun checkObservingStatus(principal: Principal, @RequestParam(required = true, name = "user") userId: Long): ResponseEntity<ObservingStatus> {
        return when (val result = getObservingStatus(principal.name, userId)) {
            is Result.Success -> ResponseEntity.ok(ObservingStatus(result.data, userId))
            is Result.Error -> throw UnexpectedException(result.exception)
        }
    }

    @PostMapping("/observing")
    fun updateObservingStatus(principal: Principal, @RequestBody observingStatus: ObservingStatus): ResponseEntity<*> {
        return ResponseEntity.ok(Unit)
    }

    @PostMapping("/user")
    fun updateUser(principal: Principal,
                   @RequestPart("bio") bio: String?,
                   @RequestPart("username") username: String?,
                   @RequestPart("name") name: String?,
                   @RequestPart("avatar") avatar: MultipartFile?): ResponseEntity<*> {
        return ResponseEntity.ok(Unit)
    }
}