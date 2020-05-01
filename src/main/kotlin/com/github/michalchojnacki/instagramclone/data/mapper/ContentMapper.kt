package com.github.michalchojnacki.instagramclone.data.mapper

import com.github.michalchojnacki.instagramclone.common.Mapper
import com.github.michalchojnacki.instagramclone.data.model.ImageUrlMapper
import com.github.michalchojnacki.instagramclone.data.model.RawContent
import com.github.michalchojnacki.instagramclone.domain.content.model.Content
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class ContentMapper @Autowired constructor(private val userMapper: UserDetailsMapper,
                                           private val imageUrlMapper: ImageUrlMapper) : Mapper<Pair<RawContent, Int>, Content> {
    override fun map(input: Pair<RawContent, Int>): Content {
        val rawContent = input.first
        val likesCount = input.second
        return Content(rawContent.id!!, imageUrlMapper.map(rawContent.imageId!!), rawContent.description!!,
                userMapper.map(rawContent.owner!!), likesCount,
                rawContent.publicationTimestamp!!)
    }
}