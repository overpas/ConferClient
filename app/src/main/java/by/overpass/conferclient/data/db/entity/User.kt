package by.overpass.conferclient.data.db.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.Relation

@Entity
data class User(
    @ColumnInfo(name = "email")
    var email: String?,
    @ColumnInfo(name = "fullName")
    var fullName: String?,
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Long,
    @ColumnInfo(name = "passwordHash")
    var passwordHash: String?,
    var posts: List<Post>?,
    @ColumnInfo(name = "username")
    var username: String?
)