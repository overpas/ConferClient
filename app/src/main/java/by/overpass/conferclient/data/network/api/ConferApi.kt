package by.overpass.conferclient.data.network.api

import by.overpass.conferclient.data.db.entity.User
import by.overpass.conferclient.data.dto.UserRegistration
import by.overpass.conferclient.data.network.dto.Author
import by.overpass.conferclient.data.network.dto.Post
import by.overpass.conferclient.data.network.dto.PostTree
import by.overpass.conferclient.data.network.dto.TokenResponse
import retrofit2.Call
import retrofit2.http.*

private const val API = "/api"
private const val API_POSTS = "$API/posts"
private const val GRANT_TYPE_PASSWORD = "password"

interface ConferApi {

    @GET("$API_POSTS/popular")
    fun getPopular(@Query("limit") limit: Int): Call<List<Post>>

    @GET("$API_POSTS/latest")
    fun getLatest(@Query("start") start: Int, @Query("size") size: Int): Call<List<Post>>

    @GET("$API_POSTS/{id}")
    fun getPostById(@Path("id") id: Long): Call<PostTree>

    @GET("$API/users/{id}")
    fun getUserById(@Path("id") id: Long): Call<User>

    @POST("$API_POSTS/create/new")
    fun createNewPost(
        @Query("postTitle") title: String,
        @Query("postBody") body: String,
        @Query("userId") userId: Long,
        @Query("inReplyTo") inReplyTo: Long,
        @Query("access_token") accessToken: String
    ): Call<PostTree>

    @POST("$API/auth/register")
    fun register(@Body userRegistration: UserRegistration): Call<Void>

    @POST("/oauth/token")
    @FormUrlEncoded
    fun login(
        @Header("Authorization") basic: String,
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("grant_type") grantType: String = GRANT_TYPE_PASSWORD
    ): Call<TokenResponse>

}