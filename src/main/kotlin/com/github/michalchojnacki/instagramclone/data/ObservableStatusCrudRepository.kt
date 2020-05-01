package com.github.michalchojnacki.instagramclone.data

import com.github.michalchojnacki.instagramclone.data.model.RawObservation
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ObservableStatusCrudRepository : CrudRepository<RawObservation, Long> {
    fun deleteByOwnerIdAndObservingId(ownerId: Long, observingId: Long): List<RawObservation>

    fun findByOwnerIdAndObservingId(ownerId: Long, observingId: Long): RawObservation?
}