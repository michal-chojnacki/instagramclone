package com.github.michalchojnacki.instagramclone.domain.content

import com.github.michalchojnacki.instagramclone.common.Result
import com.github.michalchojnacki.instagramclone.domain.content.model.Content
import com.github.michalchojnacki.instagramclone.domain.content.model.Image
import com.github.michalchojnacki.instagramclone.domain.content.model.User
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

@Service
class GetMainContentForUserUseCase {
    operator fun invoke(username: String) : Result<List<Content>> {
        val user1 = User("Cristiano Ronaldo", Image("https://avatarfiles.alphacoders.com/127/127408.png"))
        val user2 = User("Leo Messi", Image("https://i1.sndcdn.com/avatars-000353021408-15cukl-t500x500.jpg"))
        return Result.Success(listOf(
                Content(Image("https://zyciorysy.info/wp-content/uploads/2018/02/Cristiano-Ronaldo.jpg"), "Hala Madrid", user1, System.currentTimeMillis()),
                Content(Image("http://weszlo.com/wp-content/uploads/2017/09/skysports-lionel-messi-barca_3825537-768x420.jpg"), "Visca el Barca", user2, System.currentTimeMillis() - TimeUnit.DAYS.toMillis(10)),
                Content(Image("https://cdn.twoje-miasto.pl/img/art/2019/11/109125g.jpg?1"), "Comemos", user1, System.currentTimeMillis() - TimeUnit.DAYS.toMillis(20))))
    }
}