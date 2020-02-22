package com.github.michalchojnacki.instagramclone.domain.content

import com.github.michalchojnacki.instagramclone.common.Result
import com.github.michalchojnacki.instagramclone.data.ContentsRepository
import com.github.michalchojnacki.instagramclone.data.ImagesRepository
import com.github.michalchojnacki.instagramclone.data.UserRepository
import com.github.michalchojnacki.instagramclone.domain.content.model.Content
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import kotlin.math.absoluteValue
import kotlin.random.Random

@Service
class AddContentUseCase @Autowired constructor(private val contentsRepository: ContentsRepository,
                                               private val imagesRepository: ImagesRepository,
                                               private val userRepository: UserRepository){

    operator fun invoke(message: String, username: String, image: MultipartFile) : Result<Unit> {
        val contentImage = when(val result = imagesRepository.saveContentImage(image, Random.nextLong().absoluteValue)){
            is Result.Success -> result.data
            is Result.Error -> return Result.Error(Exception("Problem saving image"))
        }
        val content = Content(contentImage, message, userRepository.getUser(username), System.currentTimeMillis())
        return contentsRepository.addContent(content)
    }
}