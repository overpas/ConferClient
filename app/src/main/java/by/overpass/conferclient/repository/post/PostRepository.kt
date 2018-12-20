package by.overpass.conferclient.repository.post

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import by.overpass.conferclient.data.network.CLIENT
import by.overpass.conferclient.data.network.api.ConferApi
import by.overpass.conferclient.data.network.dto.PostTree
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class PostRepository(context: Context) {

    private val conferApi = CLIENT.create(ConferApi::class.java)

    fun getPostTreeById(id: Long): LiveData<PostTree> {
        val postTreeData = MutableLiveData<PostTree>()
        conferApi.getPostById(id).enqueue(object : Callback<PostTree> {
            override fun onFailure(call: Call<PostTree>, t: Throwable) {
                Timber.e(t)
            }

            override fun onResponse(call: Call<PostTree>, response: Response<PostTree>) {
                if (response.isSuccessful && response.body() != null) {
                    postTreeData.value = response.body()!!
                }
            }
        })
        return postTreeData
    }

}
