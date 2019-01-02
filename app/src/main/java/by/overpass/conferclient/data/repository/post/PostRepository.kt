package by.overpass.conferclient.data.repository.post

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.content.Context
import by.overpass.conferclient.R
import by.overpass.conferclient.data.callback.PostTreeCallback
import by.overpass.conferclient.data.db.ConferDatabase
import by.overpass.conferclient.data.dto.Status
import by.overpass.conferclient.data.mapper.PostTreeMapper
import by.overpass.conferclient.data.network.CLIENT
import by.overpass.conferclient.data.network.api.ConferApi
import by.overpass.conferclient.data.network.dto.PostTree

class PostRepository(private val progress: MutableLiveData<Status>, context: Context) {

    private val conferApi = CLIENT.create(ConferApi::class.java)
    private val defaultErrorMessage = context.getString(R.string.default_error_message)
    private val postTreeDao = ConferDatabase.getInstance().getPostTreeDao()
    private val mapper = PostTreeMapper()

    fun getPostTreeById(id: Long): LiveData<PostTree> {
        progress.value = Status.Loading
        conferApi.getPostById(id).enqueue(
            PostTreeCallback(
                progress, defaultErrorMessage, mapper, postTreeDao
            )
        )
        return Transformations.map(postTreeDao.findById(id)) {
            it?.run {
                mapper.map(this)
            }
        }
    }

}
