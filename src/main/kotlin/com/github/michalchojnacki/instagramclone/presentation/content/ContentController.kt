package com.github.michalchojnacki.instagramclone.presentation.content

import com.github.michalchojnacki.instagramclone.common.Result
import com.github.michalchojnacki.instagramclone.domain.common.UnexpectedException
import com.github.michalchojnacki.instagramclone.domain.content.*
import com.github.michalchojnacki.instagramclone.presentation.content.model.ContentsResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.security.Principal

@RestController
class ContentController @Autowired constructor(private val getMainContentForUser: GetMainContentForUserUseCase,
                                               private val getRecommendedContent: GetRecommendedContentUseCase,
                                               private val getUserContent: GetUserContentUseCase,
                                               private val searchForContent: SearchForContentUseCase,
                                               private val addContent: AddContentUseCase) {
    @GetMapping("/main_content")
    fun provideMainContent(): ResponseEntity<ContentsResponse> {
        return when (val result = getMainContentForUser(SecurityContextHolder.getContext().authentication.name)) {
            is Result.Success -> ResponseEntity.ok(ContentsResponse(result.data))
            is Result.Error -> when (val e = result.exception) {
                else -> throw UnexpectedException(e)
            }
        }
    }

    @GetMapping("/recommended_content")
    fun provideRecommendedContent(): ResponseEntity<ContentsResponse> {
        return when (val result = getRecommendedContent(SecurityContextHolder.getContext().authentication.name)) {
            is Result.Success -> ResponseEntity.ok(ContentsResponse(result.data))
            is Result.Error -> when (val e = result.exception) {
                else -> throw UnexpectedException(e)
            }
        }
    }

    @GetMapping("/search_content")
    fun provideSearchContent(@RequestParam(required = true) query: String): ResponseEntity<ContentsResponse> {
        return when (val result = searchForContent(query)) {
            is Result.Success -> ResponseEntity.ok(ContentsResponse(result.data))
            is Result.Error -> when (val e = result.exception) {
                else -> throw UnexpectedException(e)
            }
        }
    }

    @GetMapping("/content")
    fun provideMainContent(@RequestParam(required = false, name = "user") userId: Long?): ResponseEntity<ContentsResponse> {
        val result = if (userId != null) {
            getUserContent(userId)
        } else {
            getUserContent(SecurityContextHolder.getContext().authentication.name)
        }
        return when (result) {
            is Result.Success -> ResponseEntity.ok(ContentsResponse(result.data))
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