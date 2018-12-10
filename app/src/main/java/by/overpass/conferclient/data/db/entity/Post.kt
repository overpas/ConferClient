package by.overpass.conferclient.data.db.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.Relation
import by.overpass.conferclient.data.network.pojo.Author
import com.google.gson.annotations.SerializedName
import java.util.*

@Entity
data class Post(
    var author: User?,
    @ColumnInfo(name = "body")
    var body: String?,
    @ColumnInfo(name = "date")
    var date: Date?,
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Long,
    @ColumnInfo(name = "inReplyTo")
    var inReplyTo: Long?,
    @ColumnInfo(name = "title")
    var title: String?
)