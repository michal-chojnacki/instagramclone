package com.github.michalchojnacki.instagramclone.domain.content

import com.github.michalchojnacki.instagramclone.common.Result
import com.github.michalchojnacki.instagramclone.data.ContentsRepository
import com.github.michalchojnacki.instagramclone.data.ImagesRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import kotlin.math.absoluteValue
import kotlin.random.Random

@Service
class AddContentUseCase @Autowired constructor(private val contentsRepository: ContentsRepository,
                                               private val imagesRepository: ImagesRepository) {

    operator fun invoke(message: String, username: String, image: MultipartFile): Result<Unit> {
        val contentImageId = Random.nextLong().absoluteValue
        when (imagesRepository.saveContentImage(image, contentImageId)) {
            is Result.Success -> Unit
            is Result.Error -> return Result.Error(Exception("Problem saving image"))
        }
        return contentsRepository.saveContent(message, username, image, contentImageId)
    }
}