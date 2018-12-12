package by.overpass.conferclient.data.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import by.overpass.conferclient.data.db.dao.PostDao
import by.overpass.conferclient.data.db.dao.UserDao
import by.overpass.conferclient.data.db.entity.Post
import by.overpass.conferclient.data.db.entity.User

@Database(entities = [Post::class, User::class], version = 1)
@TypeConverters(DateTypeConverter::class)
abstract class ConferDatabase : RoomDatabase() {

    abstract fun getPostDao(): PostDao

    abstract fun getUserDao(): UserDao

    companion object {
        private var instance: ConferDatabase? = null

        fun getInstance(context: Context) = instance ?: synchronized(this) {
            instance ?: Room.databaseBuilder(context, ConferDatabase::class.java, "confer.db")
                .build()
                .also { instance = it }
        }
    }

}