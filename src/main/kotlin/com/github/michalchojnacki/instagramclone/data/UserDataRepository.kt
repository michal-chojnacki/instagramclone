package com.github.michalchojnacki.instagramclone.data

import com.github.michalchojnacki.instagramclone.common.Result
import com.github.michalchojnacki.instagramclone.data.model.RawLike
import com.github.michalchojnacki.instagramclone.presentation.userdata.model.LikeStatuses
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
class UserDataRepository @Autowired constructor(
        private val userDataCrudRepository: UserDataCrudRepository) {

    @Transactional
    fun saveLikeStatus(userId: Long, contentId: Long, status: Boolean): Result<Unit> {
        if (status) {
            userDataCrudRepository.save(RawLike(userId, contentId))
        } else {
            userDataCrudRepository.deleteByUserIdAndContentId(userId, contentId)
        }
        return Result.Success(Unit)
    }

    fun loadLikeStatuses(userId: Long, contentIds: List<Long>): Result<LikeStatuses> {
        val userLikes = userDataCrudRepository.findByUserId(userId).mapNotNull { it.contentId }
        return Result.Success(LikeStatuses(contentIds.map { it to userLikes.contains(it) }.toMap()))
    }
}