package com.github.michalchojnacki.instagramclone.data.mapper

import com.github.michalchojnacki.instagramclone.common.Mapper
import com.github.michalchojnacki.instagramclone.data.model.RawUserDetails
import com.github.michalchojnacki.instagramclone.domain.content.model.UserDetails
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class UserDetailsMapper @Autowired constructor(private val avatarUrlMapper: AvatarUrlMapper) : Mapper<RawUserDetails, UserDetails> {
    override fun map(input: RawUserDetails): UserDetails {
        return UserDetails(input.id!!, input.username!!, avatarUrlMapper.map(input.avatarId ?: 0), input.bio
                ?: "", input.fullname ?: "")
    }
}