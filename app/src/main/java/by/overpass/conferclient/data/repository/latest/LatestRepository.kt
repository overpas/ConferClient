package by.overpass.conferclient.data.repository.latest

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
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

    private val postDao = ConferDatabase.getInstance().getPostDao()
    private val userDao = ConferDatabase.getInstance().getUserDao()
    private val conferApi = CLIENT.create(ConferApi::class.java)

    private val config: PagedList.Config by lazy {
        PagedList.Config.Builder()
            .setPageSize(PAGE_SIZE)
            .build()
    }

    val progress = MutableLiveData<Status>()

    val boundaryCallback by lazy {
        LatestPostBoundaryCallback(
            conferApi,
            postDao,
            userDao,
            Mapper(),
            progress,
            PAGE_SIZE
        )
    }

    val pagedPostData: LiveData<PagedList<PostWithUser>> by lazy {
        val dataSourceFactory = postDao.findLatestPaged()
        return@lazy buildLiveData(dataSourceFactory)
    }

    fun getPagedLocalPosts(text: String): LiveData<PagedList<PostWithUser>> {
        val dataSourceFactory = postDao.findLatestLocallyByText("%$text%")
        return buildLiveData(dataSourceFactory)
    }

    private fun buildLiveData(
        dataSourceFactory: DataSource.Factory<Int, PostWithUser>
    ): LiveData<PagedList<PostWithUser>> {
        return LivePagedListBuilder(dataSourceFactory, config)
            .setFetchExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
            .setBoundaryCallback(boundaryCallback)
            .build()
    }

}