package com.github.michalchojnacki.instagramclone.domain.authentication

import com.github.michalchojnacki.instagramclone.common.Result
import com.github.michalchojnacki.instagramclone.data.UsersRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class RegisterNewUserUseCase @Autowired constructor(private val usersRepository: UsersRepository) {
    @Transactional
    operator fun invoke(username: String, password: String) : Result<Unit> {
        usersRepository.findByUsername(username)
                .takeIf { it is Result.Success }
                ?.let { return Result.Error(Exception("User with username $username exists!")) }
        return usersRepository.registerUser(username, password)
    }
}