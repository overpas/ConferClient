package by.overpass.conferclient.data.network.pojo

import com.google.gson.annotations.SerializedName

data class Post(
    @SerializedName("author")
    var author: Author?,
    @SerializedName("body")
    var body: String?,
    @SerializedName("date")
    var date: Long?,
    @SerializedName("id")
    var id: Int,
    @SerializedName("inReplyTo")
    var inReplyTo: Any?,
    @SerializedName("title")
    var title: String?
)