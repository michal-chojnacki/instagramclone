package com.github.michalchojnacki.instagramclone.data

import com.github.michalchojnacki.instagramclone.common.Result
import com.github.michalchojnacki.instagramclone.domain.content.model.Content
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Repository
import java.util.concurrent.TimeUnit

@Scope("singleton")
@Repository
class ContentsRepository @Autowired constructor(private val imagesRepository: ImagesRepository,
                                                private val userRepository: UserRepository) {
    private val contents : MutableList<Content>
    init {
        val user1 = userRepository.getUser("Cristiano Ronaldo")
        val user2 = userRepository.getUser("Leo Messi")
        val user3 = userRepository.getUser("Zenon")
        contents = mutableListOf(
                Content(imagesRepository.getContentImage(4), "Disco !!", user3, System.currentTimeMillis()),
                Content(imagesRepository.getContentImage(1), "Hala Madrid", user1, System.currentTimeMillis() - TimeUnit.DAYS.toMillis(4)),
                Content(imagesRepository.getContentImage(2), "Visca el Barca", user2, System.currentTimeMillis() - TimeUnit.DAYS.toMillis(10)),
                Content(imagesRepository.getContentImage(3), "Comemos", user1, System.currentTimeMillis() - TimeUnit.DAYS.toMillis(20)))
    }

    fun loadAllContents() : Result<List<Content>> {
        return Result.Success(contents.toList())
    }

    fun addContent(content: Content) : Result<Unit> {
        contents.add(0, content)
        return Result.Success(Unit)
    }
}