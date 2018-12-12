package by.overpass.conferclient.data.db.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import by.overpass.conferclient.data.db.entity.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(users: List<User>)

    @Query("SELECT COUNT(*) FROM User")
    fun count(): Long

    @Query("SELECT * FROM User")
    fun findAll(): LiveData<List<User>>

    @Query("SELECT * FROM User WHERE user_id = :id")
    fun findById(id: Long): LiveData<User>

    @Query("DELETE FROM User")
    fun deleteAll()

    @Delete
    fun delete(user: User)
    
}