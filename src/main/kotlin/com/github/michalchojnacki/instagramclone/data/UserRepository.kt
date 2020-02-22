package com.github.michalchojnacki.instagramclone.data

import com.github.michalchojnacki.instagramclone.domain.content.model.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class UserRepository @Autowired constructor(private val imagesRepository: ImagesRepository) {
    fun getUser(username: String): User {
        return when (username) {
            "Cristiano Ronaldo" -> User(username, imagesRepository.getAvatarImage(1))
            "Leo Messi" -> User(username, imagesRepository.getAvatarImage(2))
            else -> User(username, imagesRepository.getAvatarImage(3))
        }
    }
}