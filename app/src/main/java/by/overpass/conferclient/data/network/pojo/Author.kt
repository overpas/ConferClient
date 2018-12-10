package by.overpass.conferclient.data.network.pojo

import com.google.gson.annotations.SerializedName

data class Author(
    @SerializedName("email")
    var email: String?,
    @SerializedName("fullName")
    var fullName: String?,
    @SerializedName("id")
    var id: Int,
    @SerializedName("passwordHash")
    var passwordHash: Any?,
    @SerializedName("posts")
    var posts: Any?,
    @SerializedName("roles")
    var roles: List<Role?>?,
    @SerializedName("username")
    var username: String?
)