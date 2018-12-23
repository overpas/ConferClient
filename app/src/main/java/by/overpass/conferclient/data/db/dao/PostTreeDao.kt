package by.overpass.conferclient.data.db.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import by.overpass.conferclient.data.db.entity.PostTree

@Dao
interface PostTreeDao {

    @Insert(onConflict = REPLACE)
    fun insert(postTree: PostTree): Long

    @Query("SELECT * FROM PostTree WHERE id = :id")
    fun findById(id: Long): LiveData<PostTree>

}