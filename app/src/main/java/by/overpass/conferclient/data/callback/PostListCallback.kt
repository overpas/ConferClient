package by.overpass.conferclient.data.callback

import android.arch.lifecycle.MutableLiveData
import by.overpass.conferclient.data.db.dao.PostDao
import by.overpass.conferclient.data.db.dao.UserDao
import by.overpass.conferclient.data.dto.Status
import by.overpass.conferclient.data.mapper.Mapper
import by.overpass.conferclient.data.network.dto.Post
import by.overpass.conferclient.util.runInBackground
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

open class PostListCallback(
    private val progress: MutableLiveData<Status>,
    private val mapper: Mapper,
    private val postDao: PostDao,
    private val userDao: UserDao
) : Callback<List<Post>> {

    protected open fun onPostsLoaded(posts: List<Post>) {}

    override fun onFailure(call: Call<List<Post>>, t: Throwable) {
        Timber.e(t)
        progress.value = Status.Error(t.localizedMessage)
    }

    override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
        if (response.isSuccessful && response.body() != null) {
            val parsedPosts = response.body()!!
            onPostsLoaded(parsedPosts)
            val dbPosts = mapper.mapPosts(parsedPosts)
            val dbUsers = mapper.mapUsers(parsedPosts)
            runInBackground {
                userDao.insert(dbUsers)
                postDao.insert(dbPosts)
            }
            progress.value = Status.Success
        }
    }

}