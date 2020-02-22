package com.github.michalchojnacki.instagramclone.data

import com.github.michalchojnacki.instagramclone.common.Result
import com.github.michalchojnacki.instagramclone.domain.content.model.Image
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Repository
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

@Repository
class ImagesRepository @Autowired constructor(@Value("\${server.port}") serverPort : String,
                                              @Value("\${server.address}")  serverAddress : String) {
    private val imagesRoot = "http://$serverAddress:$serverPort/images"

    fun getContentImage(id: Long) : Image {
        return Image("$imagesRoot/contents/$id.jpg")
    }

    fun getAvatarImage(id: Long) : Image {
        return Image("$imagesRoot/users/$id.jpg")
    }

    fun saveContentImage(image: MultipartFile, id: Long) : Result<Image> {
        val resource = ImagesRepository::class.java.getResource("/static/images/contents")
        val newFile = File("${resource.path}/$id.jpg")

        try {
            if (!newFile.exists()) {
                newFile.createNewFile()
            }
            var read = 0
            val bytes = ByteArray(1024)
            val outputStream = FileOutputStream(newFile)
            val inputStream = image.inputStream
            while (inputStream.read(bytes).also { read = it } != -1) {
                outputStream.write(bytes, 0, read)
            }
        } catch (e: IOException) {
            return Result.Error(e)
        }

        return Result.Success(getContentImage(id))
    }
}