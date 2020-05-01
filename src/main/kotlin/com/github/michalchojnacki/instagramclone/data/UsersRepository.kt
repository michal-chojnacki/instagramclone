package com.github.michalchojnacki.instagramclone.data

import com.github.michalchojnacki.instagramclone.common.Result
import com.github.michalchojnacki.instagramclone.data.mapper.UserDetailsMapper
import com.github.michalchojnacki.instagramclone.data.model.RawObservation
import com.github.michalchojnacki.instagramclone.data.model.RawUserDetails
import com.github.michalchojnacki.instagramclone.domain.content.model.UserDetails
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
class UsersRepository @Autowired constructor(private val userCrudRepository: UsersCrudRepository,
                                             private val observableStatusCrudRepository: ObservableStatusCrudRepository,
                                             private val userMapper: UserDetailsMapper) {

    fun findByUsername(username: String): Result<UserDetails> {
        return userCrudRepository.findByUsername(username)?.let { Result.Success(userMapper.map(it)) }
                ?: Result.Error(Exception("No user with username $username"))
    }

    fun registerUser(username: String, password: String): Result<Unit> {
        userCrudRepository.save(RawUserDetails(username, password))
        return Result.Success(Unit)
    }

    fun updateUserProfile(userId: Long, bio: String?, username: String?, fullname: String?): Result<Unit> {
        val userDetailsOptional = userCrudRepository.findById(userId)
        val userDetails = if (userDetailsOptional.isPresent) {
            userDetailsOptional.get()
        } else {
            return Result.Error(Exception("No user with id $userId"))
        }
        bio?.let { userDetails.bio = it }
        username?.let { userDetails.username = it }
        fullname?.let { userDetails.fullname = it }
        userCrudRepository.save(userDetails)
        return Result.Success(Unit)
    }

    fun findUserDetailsByUsername(username: String): Result<User> {
        val rawUserDetails = userCrudRepository.findByUsername(username)
                ?: return Result.Error(Exception("No user with username $username"))
        return Result.Success(rawUserDetails.let { User(it.username, it.password, emptyList()) })
    }

    fun getRecommendedUsers(username: String): Result<List<UserDetails>> {
        return Result.Success(userCrudRepository.findAll().filter { it.username != username }.map { userMapper.map(it) })
    }

    @Transactional
    fun saveObservingStatus(userId: Long, observableUserId: Long, status: Boolean): Result<Unit> {
        if (status) {
            observableStatusCrudRepository.save(RawObservation(userId, observableUserId))
        } else {
            observableStatusCrudRepository.deleteByOwnerIdAndObservingId(userId, observableUserId)
        }
        return Result.Success(Unit)
    }

    fun loadObservingStatus(userId: Long, observableUserId: Long): Result<Boolean> {
        val status = observableStatusCrudRepository.findByOwnerIdAndObservingId(userId, observableUserId)
        return Result.Success(status != null)
    }
}