package com.github.michalchojnacki.instagramclone.domain.profile

import com.github.michalchojnacki.instagramclone.common.Result
import com.github.michalchojnacki.instagramclone.data.UsersRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class UpdateUserUseCase @Autowired constructor(
        private val getUserDetails: GetUserDetailsUseCase,
        private val usersRepository: UsersRepository) {
    operator fun invoke(currentUsername: String, bio: String? = null,
                        username: String? = null, fullname: String? = null,
                        avatar: MultipartFile? = null): Result<Unit> {
        val userId = when (val result = getUserDetails.invoke(currentUsername)) {
            is Result.Success -> result.data.id
            is Result.Error -> return Result.Error(result.exception)
        }
        usersRepository.updateUserProfile(userId, bio, username, fullname)
        return Result.Success(Unit)
    }

}