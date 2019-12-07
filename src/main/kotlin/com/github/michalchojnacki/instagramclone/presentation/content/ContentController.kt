package com.github.michalchojnacki.instagramclone.presentation.content

import com.github.michalchojnacki.instagramclone.common.Result
import com.github.michalchojnacki.instagramclone.domain.common.UnexpectedException
import com.github.michalchojnacki.instagramclone.domain.content.GetMainContentForUserUseCase
import com.github.michalchojnacki.instagramclone.presentation.content.model.MainContentResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ContentController {
    @Autowired
    private lateinit var getMainContentForUser: GetMainContentForUserUseCase

    @GetMapping("/main_content")
    fun provideMainContent() : ResponseEntity<MainContentResponse> {
        return when(val result = getMainContentForUser(SecurityContextHolder.getContext().authentication.name)) {
            is Result.Success -> ResponseEntity.ok(MainContentResponse(result.data))
            is Result.Error -> when(val e = result.exception) {
                else -> throw UnexpectedException(e)
            }
        }
    }

}