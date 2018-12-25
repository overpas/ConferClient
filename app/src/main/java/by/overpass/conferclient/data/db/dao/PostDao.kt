package by.overpass.conferclient.data.db.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import by.overpass.conferclient.data.db.entity.Post
import by.overpass.conferclient.data.dto.PostWithUser

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

    @Query("SELECT COUNT(*) FROM Post WHERE inReplyTo = :id")
    fun findPopularityById(id: Long): Long

    // Seems to work
    @Query(
        """SELECT post1.*, User.* FROM Post as post1 LEFT JOIN User ON post1.userId = User.user_id
        ORDER BY (SELECT COUNT(*) FROM Post as post2 WHERE post2.inReplyTo = post1.post_id)
        DESC LIMIT :limit"""
    )
    fun findMostPopularWithUser(limit: Long): LiveData<List<PostWithUser>>

    @Query(
        """SELECT post1.*, User.* FROM Post as post1
            LEFT JOIN User ON post1.userId = User.user_id
            WHERE post1.title LIKE :text OR post1.body LIKE :text
            OR User.fullName LIKE :text OR User.username LIKE :text
            ORDER BY (SELECT COUNT(*) FROM Post as post2 WHERE post2.inReplyTo = post1.post_id)
            DESC LIMIT :limit"""
    )
    fun findMostPopularWithUser(limit: Long, text: String): LiveData<List<PostWithUser>>

    @Query("DELETE FROM Post")
    fun deleteAll()

    @Delete
    fun delete(post: Post)

}