package com.github.michalchojnacki.instagramclone.data.mapper

import com.github.michalchojnacki.instagramclone.common.Mapper
import com.github.michalchojnacki.instagramclone.domain.content.model.Image
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

@Component
class AvatarUrlMapper @Autowired constructor(@Qualifier("imagesRoot") private val imagesRoot : String) : Mapper<Long, Image> {
    override fun map(input: Long): Image {
        return Image("$imagesRoot/users/$input.jpg")
    }
}