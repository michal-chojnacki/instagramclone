package com.github.michalchojnacki.instagramclone.data

import com.github.michalchojnacki.instagramclone.common.Result
import com.github.michalchojnacki.instagramclone.data.model.ImageUrlMapper
import com.github.michalchojnacki.instagramclone.domain.content.model.Image
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Repository
import org.springframework.web.multipart.MultipartFile
import java.io.File

@Repository
class ImagesRepository @Autowired constructor(private val imageUrlMapper: ImageUrlMapper,
                                              @Value("\${app.extras.path}") private val imagesPath: String) {

    fun saveContentImage(image: MultipartFile, id: Long): Result<Image> {
        image.transferTo(File("$imagesPath/images/contents/$id.jpg"))
        return Result.Success(imageUrlMapper.map(id))
    }
}