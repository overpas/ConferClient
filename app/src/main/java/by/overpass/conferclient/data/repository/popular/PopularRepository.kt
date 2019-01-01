package by.overpass.conferclient.data.repository.popular

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import by.overpass.conferclient.R
import by.overpass.conferclient.data.callback.PostListCallback
import by.overpass.conferclient.data.db.ConferDatabase
import by.overpass.conferclient.data.dto.PostWithUser
import by.overpass.conferclient.data.dto.Status
import by.overpass.conferclient.data.mapper.Mapper
import by.overpass.conferclient.data.network.CLIENT
import by.overpass.conferclient.data.network.api.ConferApi
import by.overpass.conferclient.data.network.dto.Post
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import java.net.SocketTimeoutException

private const val DEFAULT_LIMIT = 10

class PopularRepository(private val progress: MutableLiveData<Status>, context: Context) {

    private val conferApi = CLIENT.create(ConferApi::class.java)
    private val defaultErrorMessage = context.getString(R.string.default_error_message)
    private val mapper = Mapper()
    private val postDao = ConferDatabase.getInstance(context.applicationContext).getPostDao()
    private val userDao = ConferDatabase.getInstance(context.applicationContext).getUserDao()

    fun getPopular(limit: Int = DEFAULT_LIMIT): LiveData<List<PostWithUser>> {
        progress.value = Status.Loading
        conferApi.getPopular(limit).enqueue(PostListCallback(progress, mapper, postDao, userDao))
        return postDao.findMostPopularWithUser(limit.toLong())
    }

    fun getPopularLocallyByText(
        text: String,
        limit: Int = DEFAULT_LIMIT
    ): LiveData<List<PostWithUser>> {
        return postDao.findMostPopularWithUser(limit.toLong(), "%$text%")
    }

}