package by.overpass.conferclient.data.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import by.overpass.conferclient.ConferApp
import by.overpass.conferclient.data.db.dao.PostDao
import by.overpass.conferclient.data.db.dao.PostTreeDao
import by.overpass.conferclient.data.db.dao.UserDao
import by.overpass.conferclient.data.db.entity.Post
import by.overpass.conferclient.data.db.entity.PostTree
import by.overpass.conferclient.data.db.entity.User

@Database(entities = [Post::class, User::class, PostTree::class], version = 2)
@TypeConverters(DateTypeConverter::class)
abstract class ConferDatabase : RoomDatabase() {

    abstract fun getPostDao(): PostDao

    abstract fun getUserDao(): UserDao

    abstract fun getPostTreeDao(): PostTreeDao

    companion object {
        private var instance: ConferDatabase? = null

        fun getInstance() = instance ?: synchronized(this) {
            instance ?: Room
                .databaseBuilder(
                    ConferApp.getAppContext(),
                    ConferDatabase::class.java,
                    "confer.db"
                )
                .fallbackToDestructiveMigration()
                .build()
                .also { instance = it }
        }
    }

}