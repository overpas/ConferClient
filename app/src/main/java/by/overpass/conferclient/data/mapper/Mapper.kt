package by.overpass.conferclient.data.mapper

import by.overpass.conferclient.data.db.entity.User
import by.overpass.conferclient.data.network.dto.Post
import java.util.*

class Mapper {

    fun mapPosts(posts: List<Post>): List<by.overpass.conferclient.data.db.entity.Post> =
        posts.map {
            by.overpass.conferclient.data.db.entity.Post(
                it.id, it.author.id, it.body, Date(it.date), it.inReplyTo, it.title
            )
        }

    fun mapUsers(posts: List<Post>): List<User> = posts.map {
        User(it.author.id, it.author.email, it.author.fullName, it.author.username)
    }

}
