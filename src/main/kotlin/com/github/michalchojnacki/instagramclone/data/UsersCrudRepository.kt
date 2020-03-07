package com.github.michalchojnacki.instagramclone.data

import com.github.michalchojnacki.instagramclone.data.model.RawUserDetails
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UsersCrudRepository : CrudRepository<RawUserDetails, Long> {
    fun findByUsername(username: String): RawUserDetails?
}