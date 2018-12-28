package by.overpass.conferclient.data.callback

import android.arch.lifecycle.MutableLiveData
import by.overpass.conferclient.data.db.dao.PostTreeDao
import by.overpass.conferclient.data.dto.Status
import by.overpass.conferclient.data.mapper.PostTreeMapper
import by.overpass.conferclient.data.network.dto.PostTree
import by.overpass.conferclient.util.runInBackground
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class PostTreeCallback(
    private val progress: MutableLiveData<Status>,
    private val defaultErrorMessage: String,
    private val mapper: PostTreeMapper,
    private val postTreeDao: PostTreeDao
) : Callback<PostTree> {

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

}