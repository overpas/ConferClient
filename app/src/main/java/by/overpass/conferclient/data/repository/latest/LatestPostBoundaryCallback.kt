package by.overpass.conferclient.data.repository.latest

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.PagedList
import by.overpass.conferclient.data.db.dao.PostDao
import by.overpass.conferclient.data.db.dao.UserDao
import by.overpass.conferclient.data.dto.PostWithUser
import by.overpass.conferclient.data.dto.Status
import by.overpass.conferclient.data.mapper.Mapper
import by.overpass.conferclient.data.network.api.ConferApi
import by.overpass.conferclient.data.network.dto.Post
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

        private var start = 0

        override fun onZeroItemsLoaded() {
            super.onZeroItemsLoaded()
            progress.value = Status.Loading
            conferApi.getLatest(start, pageSize).enqueue(object : Callback<List<Post>> {
                override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                    Timber.e(t)
                    progress.value = Status.Error(t.localizedMessage)
                }

                override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                    if (response.isSuccessful && response.body() != null) {
                        val parsedPosts = response.body()!!
                        start += parsedPosts.size
                        val dbPosts = mapper.mapPosts(parsedPosts)
                        val dbUsers = mapper.mapUsers(parsedPosts)
                        runInBackground {
                            userDao.insert(dbUsers)
                            postDao.insert(dbPosts)
                        }
                        progress.value = Status.Success
                    }
                }

            })
        }

        override fun onItemAtEndLoaded(itemAtEnd: PostWithUser) {
            super.onItemAtEndLoaded(itemAtEnd)
            // TODO: Implement further loading
        }

    }