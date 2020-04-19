package com.github.michalchojnacki.instagramclone.presentation.userdata

import com.github.michalchojnacki.instagramclone.common.Result
import com.github.michalchojnacki.instagramclone.domain.common.UnexpectedException
import com.github.michalchojnacki.instagramclone.domain.userdata.GetLikeStatusesUseCase
import com.github.michalchojnacki.instagramclone.domain.userdata.SetLikeStatusUseCase
import com.github.michalchojnacki.instagramclone.presentation.userdata.model.LikeStatus
import com.github.michalchojnacki.instagramclone.presentation.userdata.model.LikeStatuses
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
class UserDataController @Autowired constructor(private val getLikeStatuses: GetLikeStatusesUseCase,
                                                private val setLikeStatus: SetLikeStatusUseCase) {
    @GetMapping("/likes")
    fun provideLikes(principal: Principal, @RequestParam(required = true, name = "ids") contentIds: List<Long>): ResponseEntity<LikeStatuses> {
        return when (val result = getLikeStatuses(principal.name, contentIds)) {
            is Result.Success -> ResponseEntity.ok(result.data)
            is Result.Error -> throw UnexpectedException(result.exception)
        }
    }

    @PostMapping("/likes")
    fun postLikes(principal: Principal, @RequestBody(required = true) likeStatus: LikeStatus): ResponseEntity<*> {
        return when (val result = setLikeStatus(principal.name, likeStatus.contentId, likeStatus.status)) {
            is Result.Success -> ResponseEntity.ok(Unit)
            is Result.Error -> throw UnexpectedException(result.exception)
        }
    }
}