package com.github.michalchojnacki.instagramclone.data

import com.github.michalchojnacki.instagramclone.common.Result
import com.github.michalchojnacki.instagramclone.data.mapper.UserDetailsMapper
import com.github.michalchojnacki.instagramclone.data.model.RawUserDetails
import com.github.michalchojnacki.instagramclone.domain.content.model.UserDetails
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Repository

@Repository
class UsersRepository @Autowired constructor(private val userCrudRepository: UsersCrudRepository,
                                             private val userMapper: UserDetailsMapper) {
    fun findByUsername(username: String): Result<UserDetails> {
        return userCrudRepository.findByUsername(username)?.let { Result.Success(userMapper.map(it)) }
                ?: Result.Error(Exception("No user with username $username"))
    }

    fun registerUser(username: String, password: String) : Result<Unit> {
        userCrudRepository.save(RawUserDetails(username, password))
        return Result.Success(Unit)
    }

    fun findUserDetailsByUsername(username: String): Result<User> {
        val rawUserDetails = userCrudRepository.findByUsername(username) ?: return Result.Error(Exception("No user with username $username"))
        return Result.Success(rawUserDetails.let { User(it.username, it.password, emptyList()) })
    }

    fun getRecommendedUsers(username: String): Result<List<UserDetails>> {
        return Result.Success(userCrudRepository.findAll().filter { it.username != username }.map { userMapper.map(it) })
    }
}