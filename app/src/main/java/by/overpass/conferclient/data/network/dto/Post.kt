package by.overpass.conferclient.data.network.dto

import com.google.gson.annotations.SerializedName

data class Post(
    @SerializedName("author")
    var author: Author,
    @SerializedName("body")
    var body: String?,
    @SerializedName("date")
    var date: Long,
    @SerializedName("id")
    var id: Long,
    @SerializedName("inReplyTo")
    var inReplyTo: Long?,
    @SerializedName("title")
    var title: String?
)