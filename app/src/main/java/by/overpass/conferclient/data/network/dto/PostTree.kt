package by.overpass.conferclient.data.network.dto

import com.google.gson.annotations.SerializedName

data class PostTree(
    @SerializedName("id")
    var id: Long,
    @SerializedName("title")
    var title: String,
    @SerializedName("body")
    var body: String,
    @SerializedName("authorId")
    var authorId: Long,
    @SerializedName("authorUsername")
    var authorUsername: String,
    @SerializedName("authorFullName")
    var authorFullName: String,
    @SerializedName("date")
    var date: Long,
    @SerializedName("replies")
    var replies: List<PostTree>?
)