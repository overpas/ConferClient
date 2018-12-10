package by.overpass.conferclient.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import by.overpass.conferclient.data.network.api.ConferApi
import by.overpass.conferclient.data.network.pojo.Post
import by.overpass.conferclient.data.network.CLIENT
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

private const val DEFAULT_LIMIT = 10

class PopularRepository {

    private val conferApi = CLIENT.create(ConferApi::class.java)

    fun getPopular(limit: Int = DEFAULT_LIMIT): LiveData<List<Post>> {
        val popularPosts = MutableLiveData<List<Post>>()
        conferApi.getPopular(limit).enqueue(object : Callback<List<Post>> {
            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
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

}