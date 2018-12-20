package by.overpass.conferclient.data.network.dto

import com.google.gson.annotations.SerializedName

data class Author(
    @SerializedName("email")
    var email: String?,
    @SerializedName("fullName")
    var fullName: String?,
    @SerializedName("id")
    var id: Long,
    @SerializedName("passwordHash")
    var passwordHash: String?,
    @SerializedName("posts")
    var posts: Post?,
    @SerializedName("roles")
    var roles: List<Role?>?,
    @SerializedName("username")
    var username: String?
)