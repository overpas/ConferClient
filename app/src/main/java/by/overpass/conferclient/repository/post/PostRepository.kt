package by.overpass.conferclient.repository.post

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.content.Context
import by.overpass.conferclient.R
import by.overpass.conferclient.data.db.ConferDatabase
import by.overpass.conferclient.data.dto.Status
import by.overpass.conferclient.data.mapper.PostTreeMapper
import by.overpass.conferclient.data.network.CLIENT
import by.overpass.conferclient.data.network.api.ConferApi
import by.overpass.conferclient.data.network.dto.PostTree
import by.overpass.conferclient.util.runInBackground
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class PostRepository(private val progress: MutableLiveData<Status>, context: Context) {

    private val conferApi = CLIENT.create(ConferApi::class.java)
    private val defaultErrorMessage = context.getString(R.string.default_error_message)
    private val postTreeDao = ConferDatabase.getInstance(context).getPostTreeDao()
    private val mapper = PostTreeMapper()

    fun getPostTreeById(id: Long): LiveData<PostTree> {
        progress.value = Status.Loading
        conferApi.getPostById(id).enqueue(object : Callback<PostTree> {
            override fun onFailure(call: Call<PostTree>, t: Throwable) {
                Timber.e(t)
                progress.value = Status.Error(t.message ?: defaultErrorMessage)
            }

            override fun onResponse(call: Call<PostTree>, response: Response<PostTree>) {
                if (response.isSuccessful && response.body() != null) {
                    val parsedPostTree = response.body()!!
                    val postTree = mapper.map(parsedPostTree)
                    runInBackground {
                        postTreeDao.insert(postTree)
                    }
                    progress.value = Status.Success
                }
            }
        })
        return Transformations.map(postTreeDao.findById(id)) {
            it?.run {
                mapper.map(this)
            }
        }
    }

}
