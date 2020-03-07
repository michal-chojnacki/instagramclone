package com.github.michalchojnacki.instagramclone.domain.content

import com.github.michalchojnacki.instagramclone.common.Result
import com.github.michalchojnacki.instagramclone.data.ContentsRepository
import com.github.michalchojnacki.instagramclone.domain.content.model.Content
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class GetUserContentUseCase @Autowired constructor(
        private val contentsRepository: ContentsRepository) {
    operator fun invoke(username: String): Result<List<Content>> {
        return contentsRepository.loadUserContents(username)
    }

    operator fun invoke(userId: Long): Result<List<Content>> {
        return contentsRepository.loadUserContents(userId)
    }
}