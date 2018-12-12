package by.overpass.conferclient.data.db.entity

import android.arch.persistence.room.*
import java.util.*

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = [User.COLUMNS.ID],
            childColumns = [Post.COLUMNS.AUTHOR_ID]
        )
    ]
)
data class Post(
    @PrimaryKey
    @ColumnInfo(name = COLUMNS.ID)
    var id: Long,
    @ColumnInfo(name = COLUMNS.AUTHOR_ID)
    var authorId: Long,
    @ColumnInfo(name = COLUMNS.BODY)
    var body: String?,
    @ColumnInfo(name = COLUMNS.DATE)
    var date: Date?,
    @ColumnInfo(name = COLUMNS.IN_REPLY_TO)
    var inReplyTo: Long?,
    @ColumnInfo(name = COLUMNS.TITLE)
    var title: String?
) {
    object COLUMNS {
        const val ID = "post_id"
        const val BODY = "body"
        const val DATE = "date"
        const val AUTHOR_ID = "userId"
        const val TITLE = "title"
        const val IN_REPLY_TO = "inReplyTo"
    }
}