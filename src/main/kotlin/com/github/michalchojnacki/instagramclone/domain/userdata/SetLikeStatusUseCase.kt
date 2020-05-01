package com.github.michalchojnacki.instagramclone.domain.userdata

import com.github.michalchojnacki.instagramclone.common.Result
import com.github.michalchojnacki.instagramclone.data.ContentsRepository
import com.github.michalchojnacki.instagramclone.domain.profile.GetUserDetailsUseCase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SetLikeStatusUseCase @Autowired constructor(private val getUserDetails: GetUserDetailsUseCase,
                                                  private val contentsRepository: ContentsRepository) {
    operator fun invoke(username: String, contentId: Long, status: Boolean): Result<Unit> {
        val userId = when (val result = getUserDetails.invoke(username)) {
            is Result.Success -> result.data.id
            is Result.Error -> return Result.Error(result.exception)
        }
        return contentsRepository.saveLikeStatus(userId, contentId, !status)
    }
}