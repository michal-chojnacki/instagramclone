package com.github.michalchojnacki.instagramclone

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class InstagramcloneApplication {

    @Bean
    @Qualifier("imagesRoot")
    fun provideImagesRoot(@Value("\${server.port}") serverPort: String,
                          @Value("\${app.extras.ip}") serverAddress: String): String {
        return "http://$serverAddress:$serverPort/images"
    }
}

fun main(args: Array<String>) {
    runApplication<InstagramcloneApplication>(*args)
}
