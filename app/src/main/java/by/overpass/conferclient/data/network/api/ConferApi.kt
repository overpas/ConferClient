package by.overpass.conferclient.data.network.api

import by.overpass.conferclient.data.network.pojo.Post
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

const val API_POSTS = "/api/posts"

interface ConferApi {

    @GET("$API_POSTS/popular")
    fun getPopular(@Query("limit") limit: Int): Call<List<Post>>

    @GET("$API_POSTS/latest")
    fun getLatest(@Query("start") start: Int, @Query("size") size: Int): Call<List<Post>>

    // TODO Implement
    @GET("$API_POSTS/{id}")
    fun getPostById(@Path("id") id: Long)

    // TODO Implement
    @POST
    fun createNewPost()

    // TODO Implement
    @POST
    fun register()

}