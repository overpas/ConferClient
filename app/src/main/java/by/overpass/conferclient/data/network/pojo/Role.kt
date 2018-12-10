package by.overpass.conferclient.data.network.pojo

import com.google.gson.annotations.SerializedName

data class Role(
    @SerializedName("id")
    var id: Int?,
    @SerializedName("name")
    var name: String?
)