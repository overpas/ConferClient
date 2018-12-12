package by.overpass.conferclient.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import by.overpass.conferclient.data.db.ConferDatabase
import by.overpass.conferclient.data.db.entity.User
import by.overpass.conferclient.data.dto.PostWithUserAndPopularity
import by.overpass.conferclient.data.network.api.ConferApi
import by.overpass.conferclient.data.network.pojo.Post
import by.overpass.conferclient.data.network.CLIENT
import by.overpass.conferclient.util.runInBackground
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import java.net.SocketTimeoutException
import java.util.*

private const val DEFAULT_LIMIT = 10

class PopularRepository(context: Context) {

    private val conferApi = CLIENT.create(ConferApi::class.java)
    private val postDao = ConferDatabase.getInstance(context).getPostDao()
    private val userDao = ConferDatabase.getInstance(context).getUserDao()

    fun getPopularNoCache(limit: Int = DEFAULT_LIMIT, retried: Boolean = false): LiveData<List<Post>> {
        val popularPosts = MutableLiveData<List<Post>>()
        conferApi.getPopular(limit).enqueue(object : Callback<List<Post>> {
            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                // TODO: Repeat if timeout (it probably means that the web app is sleeping)
                if (t is SocketTimeoutException && !retried) {
                    getPopularNoCache(limit, true)
                }
                Timber.e(t)
            }

            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                if (response.isSuccessful && response.body() != null) {
                    popularPosts.value = response.body()
                }
            }

        })
        return popularPosts
    }

    fun getPopular(limit: Int = DEFAULT_LIMIT, retried: Boolean = false): LiveData<List<PostWithUserAndPopularity>> {
        conferApi.getPopular(limit).enqueue(object : Callback<List<Post>> {
            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                // TODO: Repeat if timeout (it probably means that the web app is sleeping)
                if (t is SocketTimeoutException && !retried) {
                    getPopular(limit, true)
                }
                Timber.e(t)
            }

            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                if (response.isSuccessful && response.body() != null) {
                    val parsedPosts: List<Post> = response.body()!!
                    val dbPosts = parsedPosts.map {
                        by.overpass.conferclient.data.db.entity.Post(
                            it.id, it.author.id, it.body, Date(it.date), it.inReplyTo, it.title
                        )
                    }
                    val dbUsers = parsedPosts.map {
                        User(it.author.id, it.author.email, it.author.fullName, it.author.username)
                    }
                    runInBackground {
                        userDao.insert(dbUsers)
                        postDao.insert(dbPosts)
                    }

                }
            }

        })
        return postDao.findMostPopularWithUser(limit.toLong())
    }

}