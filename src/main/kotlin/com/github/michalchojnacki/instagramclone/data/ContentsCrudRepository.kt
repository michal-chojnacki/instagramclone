package com.github.michalchojnacki.instagramclone.data

import com.github.michalchojnacki.instagramclone.data.model.RawContent
import org.springframework.data.domain.Sort
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface ContentsCrudRepository : PagingAndSortingRepository<RawContent, Long> {
    fun findByOwnerId(ownerId: Long, sort: Sort) : List<RawContent>

    fun findByOwnerFullnameOrOwnerBio(ownerName: String, ownerBio : String) : List<RawContent>
}