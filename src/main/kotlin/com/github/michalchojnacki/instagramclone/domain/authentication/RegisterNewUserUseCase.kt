package com.github.michalchojnacki.instagramclone.domain.authentication

import com.github.michalchojnacki.instagramclone.common.Result
import org.springframework.stereotype.Service

@Service
class RegisterNewUserUseCase {
    operator fun invoke(username: String, password: String) : Result<Unit> {
        return Result.Success(Unit)
    }
}