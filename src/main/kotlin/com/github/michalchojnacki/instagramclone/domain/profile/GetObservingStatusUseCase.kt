package com.github.michalchojnacki.instagramclone.domain.profile

import com.github.michalchojnacki.instagramclone.common.Result
import com.github.michalchojnacki.instagramclone.data.UsersRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class GetObservingStatusUseCase @Autowired constructor(private val getUserDetails: GetUserDetailsUseCase,
                                                       private val usersRepository: UsersRepository) {
    operator fun invoke(username: String, observableUserId: Long): Result<Boolean> {
        val userId = when (val result = getUserDetails.invoke(username)) {
            is Result.Success -> result.data.id
            is Result.Error -> return Result.Error(result.exception)
        }
        return usersRepository.loadObservingStatus(userId, observableUserId)
    }
}