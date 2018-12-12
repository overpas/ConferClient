package by.overpass.conferclient.data.db.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import by.overpass.conferclient.data.db.entity.Post
import by.overpass.conferclient.data.dto.PostWithUser
import by.overpass.conferclient.data.dto.PostWithUserAndPopularity

@Dao
interface PostDao {

    @Insert(onConflict = REPLACE)
    fun insert(post: Post): Long

    @Insert(onConflict = REPLACE)
    fun insert(posts: List<Post>)

    @Query("SELECT COUNT(*) FROM Post")
    fun count(): Long

    @Query("SELECT * FROM Post")
    fun findAll(): LiveData<List<Post>>

    @Query("SELECT * FROM Post WHERE post_id = :id")
    fun findById(id: Long): LiveData<Post>

    @Query("SELECT * FROM Post LEFT JOIN User ON Post.userId = User.user_id GROUP BY Post.post_id")
    fun findAllWithUser(): LiveData<List<PostWithUser>>

    // TODO: Whether it will work
    @Query("SELECT post1.post_id, post1.inReplyTo, post1.userId, post1.body, post1.date, post1.title," +
            " User.user_id, User.username, User.fullName, User.email, " +
            " COUNT(post2.post_id) as popularity FROM Post as post1 " +
            "INNER JOIN Post as post2 ON post2.inReplyTo = post1.post_id " +
            "LEFT JOIN User ON post1.userId = User.user_id " +
            "GROUP BY post1.post_id ORDER BY popularity LIMIT :limit")
    fun findMostPopularWithUser(limit: Long): LiveData<List<PostWithUserAndPopularity>>

    @Query("DELETE FROM Post")
    fun deleteAll()

    @Delete
    fun delete(post: Post)

}