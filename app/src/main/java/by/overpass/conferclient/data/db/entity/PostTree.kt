package by.overpass.conferclient.data.db.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class PostTree(
    @ColumnInfo(name = "id")
    @PrimaryKey
    var id: Long,
    @ColumnInfo(name = "json")
    var json: String
)