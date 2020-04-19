package com.github.michalchojnacki.instagramclone.data.mapper

import com.github.michalchojnacki.instagramclone.common.Mapper
import com.github.michalchojnacki.instagramclone.data.model.ImageUrlMapper
import com.github.michalchojnacki.instagramclone.data.model.RawContent
import com.github.michalchojnacki.instagramclone.domain.content.model.Content
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class ContentMapper @Autowired constructor(private val userMapper: UserDetailsMapper,
                                           private val imageUrlMapper: ImageUrlMapper) : Mapper<RawContent, Content> {
    override fun map(input: RawContent): Content {
        return Content(input.id!!, imageUrlMapper.map(input.imageId!!), input.description!!,
                userMapper.map(input.owner!!), 5,
                input.publicationTimestamp!!)
    }
}