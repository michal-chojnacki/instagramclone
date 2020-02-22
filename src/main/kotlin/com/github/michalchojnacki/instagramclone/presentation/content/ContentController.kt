package com.github.michalchojnacki.instagramclone.presentation.content

import com.github.michalchojnacki.instagramclone.common.Result
import com.github.michalchojnacki.instagramclone.domain.common.UnexpectedException
import com.github.michalchojnacki.instagramclone.domain.content.AddContentUseCase
import com.github.michalchojnacki.instagramclone.domain.content.GetMainContentForUserUseCase
import com.github.michalchojnacki.instagramclone.presentation.content.model.MainContentResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.security.Principal

@RestController
class ContentController @Autowired constructor(private val getMainContentForUser: GetMainContentForUserUseCase,
                                               private val addContent: AddContentUseCase) {
    @GetMapping("/main_content")
    fun provideMainContent(): ResponseEntity<MainContentResponse> {
        return when (val result = getMainContentForUser(SecurityContextHolder.getContext().authentication.name)) {
            is Result.Success -> ResponseEntity.ok(MainContentResponse(result.data))
            is Result.Error -> when (val e = result.exception) {
                else -> throw UnexpectedException(e)
            }
        }
    }

    @PostMapping("/content")
    fun postContent(principal: Principal,
                    @RequestPart("message") message: String,
                    @RequestPart("image") image: MultipartFile): ResponseEntity<*> {
        return when (val result = addContent(message, principal.name, image)) {
            is Result.Success -> ResponseEntity.ok(Unit)
            is Result.Error -> throw UnexpectedException(result.exception)
        }
    }
}