package by.overpass.conferclient.data.db.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.Relation

val GUEST = User(-1, "", "Guest", "")

@Entity
data class User(
    @PrimaryKey
    @ColumnInfo(name = COLUMNS.ID)
    var id: Long,
    @ColumnInfo(name = COLUMNS.EMAIL)
    var email: String?,
    @ColumnInfo(name = COLUMNS.FULL_NAME)
    var fullName: String?,
    @ColumnInfo(name = COLUMNS.USERNAME)
    var username: String?
) {
    object COLUMNS {
        const val ID = "user_id"
        const val EMAIL = "email"
        const val FULL_NAME = "fullName"
        const val USERNAME = "username"
    }
}