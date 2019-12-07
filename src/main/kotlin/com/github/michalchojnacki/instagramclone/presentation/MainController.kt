package com.github.michalchojnacki.instagramclone.presentation

import org.springframework.web.bind.annotation.*

@RestController
class MainController {
    @GetMapping("/")
    fun elo() : String {
        return "elo"
    }

}