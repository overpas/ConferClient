package by.overpass.conferclient.data.repository.latest

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.PagedList
import by.overpass.conferclient.data.callback.PagedPostListCallback
import by.overpass.conferclient.data.db.dao.PostDao
import by.overpass.conferclient.data.db.dao.UserDao
import by.overpass.conferclient.data.dto.PostWithUser
import by.overpass.conferclient.data.dto.Status
import by.overpass.conferclient.data.mapper.Mapper
import by.overpass.conferclient.data.network.api.ConferApi
import by.overpass.conferclient.data.network.dto.Post
import by.overpass.conferclient.util.IntWrapper
import by.overpass.conferclient.util.runInBackground
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class LatestPostBoundaryCallback(
    private val conferApi: ConferApi,
    private val postDao: PostDao,
    private val userDao: UserDao,
    private val mapper: Mapper,
    private val progress: MutableLiveData<Status>,
    private val pageSize: Int
) : PagedList.BoundaryCallback<PostWithUser>() {

    private var start = IntWrapper(0)

    override fun onZeroItemsLoaded() {
        super.onZeroItemsLoaded()
        if (progress.value != Status.Loading) {
            loadPortion()
        }
    }

    override fun onItemAtEndLoaded(itemAtEnd: PostWithUser) {
        super.onItemAtEndLoaded(itemAtEnd)
        if (progress.value != Status.Loading) {
            loadPortion()
        }
    }

    internal fun loadPortion() {
        progress.value = Status.Loading
        conferApi.getLatest(start.value, pageSize).enqueue(
            PagedPostListCallback(start, progress, mapper, postDao, userDao)
        )
    }

}