package com.github.michalchojnacki.instagramclone.domain.profile

import com.github.michalchojnacki.instagramclone.common.Result
import com.github.michalchojnacki.instagramclone.data.UsersRepository
import com.github.michalchojnacki.instagramclone.domain.content.model.UserDetails
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class GetUserDetailsUseCase @Autowired constructor(private val usersRepository: UsersRepository) {
    operator fun invoke(username: String): Result<UserDetails> {
        return usersRepository.findByUsername(username)
    }
}