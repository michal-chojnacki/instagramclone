package com.github.michalchojnacki.instagramclone.domain.profile

import com.github.michalchojnacki.instagramclone.common.Result
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class GetObservingStatusUseCase @Autowired constructor() {
    operator fun invoke(currentUsername: String, observingUserId: Long): Result<Boolean> = Result.Success(true)
}