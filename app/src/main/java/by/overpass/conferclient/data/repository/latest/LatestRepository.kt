package by.overpass.conferclient.data.repository.latest

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import android.content.Context
import android.os.AsyncTask
import by.overpass.conferclient.data.db.ConferDatabase
import by.overpass.conferclient.data.dto.PostWithUser
import by.overpass.conferclient.data.dto.Status
import by.overpass.conferclient.data.mapper.Mapper
import by.overpass.conferclient.data.network.CLIENT
import by.overpass.conferclient.data.network.api.ConferApi

private const val PAGE_SIZE = 10

class LatestRepository(context: Context) {

    private val postDao = ConferDatabase.getInstance(context).getPostDao()
    private val userDao = ConferDatabase.getInstance(context).getUserDao()
    private val conferApi = CLIENT.create(ConferApi::class.java)

    val progress = MutableLiveData<Status>()

    val pagedPostData: LiveData<PagedList<PostWithUser>> by lazy {
        val dataSourceFactory = postDao.findLatestPaged()
        val config = PagedList.Config.Builder()
            .setPageSize(PAGE_SIZE)
            .build()
        return@lazy LivePagedListBuilder(dataSourceFactory, config)
            .setFetchExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
            .setBoundaryCallback(
                LatestPostBoundaryCallback(
                    conferApi,
                    postDao,
                    userDao,
                    Mapper(),
                    progress,
                    PAGE_SIZE
                )
            )
            .build()
    }

}