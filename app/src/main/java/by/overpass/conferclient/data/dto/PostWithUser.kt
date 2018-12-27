package by.overpass.conferclient.data.dto

import android.arch.persistence.room.Embedded
import by.overpass.conferclient.data.db.entity.Post
import by.overpass.conferclient.data.db.entity.User

data class PostWithUser(
    @Embedded var post: Post,
    @Embedded var user: User
) {
    fun getPostId() = post.id
    fun getTitle() = post.title
    fun getBody() = post.body
    fun getDate() = post.date
    fun getInReplyTo() = post.inReplyTo
    fun getFullName() = user.fullName
    fun getUsername() = user.username
    fun getUserId() = user.id
    fun getUserEmail() = user.email
}