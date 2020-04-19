package com.github.michalchojnacki.instagramclone.data

import com.github.michalchojnacki.instagramclone.common.Result
import com.github.michalchojnacki.instagramclone.presentation.userdata.model.LikeStatuses
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class UserDataRepository @Autowired constructor() {
    private val likeStatuses = mutableMapOf<Long, LikeStatuses>()

    fun saveLikeStatus(userId: Long, contentId: Long, status: Boolean): Result<Unit> {
        val userLikeStatuses = likeStatuses[userId]?.statuses ?: emptyMap()
        likeStatuses[userId] = LikeStatuses(userLikeStatuses.toMutableMap().apply {
            this[contentId] = status
        }.toMap())
        return Result.Success(Unit)
    }

    fun loadLikeStatuses(userId: Long, contentIds: List<Long>): Result<LikeStatuses> {
        val userLikeStatuses = likeStatuses[userId]?.statuses ?: emptyMap()
        return Result.Success(LikeStatuses(contentIds.map { it to (userLikeStatuses[it] ?: false) }.toMap()))
    }
}