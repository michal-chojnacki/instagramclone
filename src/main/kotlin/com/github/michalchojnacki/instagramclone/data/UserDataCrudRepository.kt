package com.github.michalchojnacki.instagramclone.data

import com.github.michalchojnacki.instagramclone.data.model.RawLike
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserDataCrudRepository : CrudRepository<RawLike, Long> {
    fun deleteByUserIdAndContentId(userId: Long, contentId: Long): List<RawLike>

    fun findByUserId(userId: Long): List<RawLike>
}