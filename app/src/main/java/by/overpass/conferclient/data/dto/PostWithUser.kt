package by.overpass.conferclient.data.dto

import android.arch.persistence.room.Embedded
import by.overpass.conferclient.data.db.entity.Post
import by.overpass.conferclient.data.db.entity.User

data class PostWithUser(
    @Embedded var post: Post,
    @Embedded var user: User
)