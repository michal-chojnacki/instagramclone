package com.github.michalchojnacki.instagramclone.data

import com.github.michalchojnacki.instagramclone.common.Result
import com.github.michalchojnacki.instagramclone.data.mapper.ContentMapper
import com.github.michalchojnacki.instagramclone.data.model.RawContent
import com.github.michalchojnacki.instagramclone.data.model.RawLike
import com.github.michalchojnacki.instagramclone.domain.content.model.Content
import com.github.michalchojnacki.instagramclone.presentation.userdata.model.LikeStatuses
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

@Repository
class ContentsRepository @Autowired constructor(private val contentsCrudRepository: ContentsCrudRepository,
                                                private val userCrudRepository: UsersCrudRepository,
                                                private val userDataCrudRepository: UserDataCrudRepository,
                                                private val contentMapper: ContentMapper) {
    fun loadAllContents(): Result<List<Content>> {
        return Result.Success(contentsCrudRepository.findAll(Sort.by(Sort.Direction.DESC, "publicationTimestamp")).mapToContent())
    }

    fun loadUserContents(username: String): Result<List<Content>> {
        val userDetailsId = userCrudRepository.findByUsername(username)?.id
                ?: return Result.Error(Exception("Error fetching user details"))
        return loadUserContents(userDetailsId)
    }

    fun loadUserContents(userId: Long): Result<List<Content>> {
        return Result.Success(contentsCrudRepository.findByOwnerId(userId, Sort.by(Sort.Direction.DESC, "publicationTimestamp")).mapToContent())
    }

    fun searchForContent(query: String): Result<List<Content>> {
        return Result.Success(contentsCrudRepository.findByOwnerFullnameOrOwnerBio(query, query).mapToContent())
    }

    fun saveContent(message: String, username: String, image: MultipartFile, contentImageId: Long): Result<Unit> {
        val userDetails = userCrudRepository.findByUsername(username)
                ?: return Result.Error(Exception("Error fetching user details"))
        val content = RawContent(message, userDetails, contentImageId, System.currentTimeMillis())
        contentsCrudRepository.save(content)
        return Result.Success(Unit)
    }

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

    private fun Iterable<RawContent>.mapToContent(): List<Content> {
        return map { contentMapper.map(it to userDataCrudRepository.countByContentId(it.id!!).toInt()) }
    }
}