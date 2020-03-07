package com.github.michalchojnacki.instagramclone

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class InstagramcloneApplication {
//    @Autowired
//    private lateinit var registerNewUserUseCase : RegisterNewUserUseCase
//    @Autowired
//    private lateinit var contentsCrudRepository : ContentsCrudRepository
//    @Autowired
//    private lateinit var userRepository: UserDetailsRepository

    @Bean
    @Qualifier("imagesRoot")
    fun provideImagesRoot(@Value("\${server.port}") serverPort: String,
                          @Value("\${server.address}") serverAddress: String): String {
        return "http://$serverAddress:$serverPort/images"
    }

//    @EventListener
//    fun onContextRefreshedEvent(event: ContextRefreshedEvent) {
//        registerNewUserUseCase.invoke("CristianoRonaldo", "CristianoRonaldo") //1
//        registerNewUserUseCase.invoke("LeoMessi", "LeoMessi") //2
//        registerNewUserUseCase.invoke("Zenon", "Zenon") //3
//        registerNewUserUseCase.invoke("elo", "elo")
//        contentsCrudRepository.save(RawContent("Disco !!", userRepository.findByUsername("Zenon")!!, 4, System.currentTimeMillis()))
//        contentsCrudRepository.save(RawContent("Hala Madrid", userRepository.findByUsername("CristianoRonaldo")!!, 1, System.currentTimeMillis()- TimeUnit.DAYS.toMillis(4)))
//        contentsCrudRepository.save(RawContent("Visca el Barca", userRepository.findByUsername("LeoMessi")!!, 2, System.currentTimeMillis()- TimeUnit.DAYS.toMillis(10)))
//        contentsCrudRepository.save(RawContent("Comemos", userRepository.findByUsername("CristianoRonaldo")!!, 3, System.currentTimeMillis()- TimeUnit.DAYS.toMillis(20)))
//    }
}

fun main(args: Array<String>) {
    runApplication<InstagramcloneApplication>(*args)
}
