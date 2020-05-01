package com.github.michalchojnacki.instagramclone.data

import com.github.michalchojnacki.instagramclone.common.Result
import com.github.michalchojnacki.instagramclone.data.model.ImageUrlMapper
import com.github.michalchojnacki.instagramclone.domain.content.model.Image
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import org.springframework.web.multipart.MultipartFile
import java.io.File

@Repository
class ImagesRepository @Autowired constructor(private val imageUrlMapper: ImageUrlMapper) {

    fun saveContentImage(image: MultipartFile, id: Long): Result<Image> {
        val resource = ImagesRepository::class.java.getResource("/static/images/contents")
        image.transferTo(File("${resource.path}/$id.jpg"))
        return Result.Success(imageUrlMapper.map(id))
    }
}