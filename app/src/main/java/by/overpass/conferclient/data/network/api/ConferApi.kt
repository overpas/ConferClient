package by.overpass.conferclient.data.network.api

import by.overpass.conferclient.data.network.pojo.Post
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ConferApi {

    @GET("/api/posts/popular")
    fun getPopular(@Query("limit") limit: Int): Call<List<Post>>

}