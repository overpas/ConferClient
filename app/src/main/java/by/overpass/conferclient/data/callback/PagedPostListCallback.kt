package by.overpass.conferclient.data.callback

import android.arch.lifecycle.MutableLiveData
import by.overpass.conferclient.data.db.dao.PostDao
import by.overpass.conferclient.data.db.dao.UserDao
import by.overpass.conferclient.data.dto.Status
import by.overpass.conferclient.data.mapper.Mapper
import by.overpass.conferclient.data.network.dto.Post
import by.overpass.conferclient.util.IntWrapper

class PagedPostListCallback(
    private var start: IntWrapper,
    progress: MutableLiveData<Status>,
    mapper: Mapper,
    postDao: PostDao,
    userDao: UserDao
) : PostListCallback(
    progress, mapper, postDao, userDao
) {

    override fun onPostsLoaded(posts: List<Post>) {
        super.onPostsLoaded(posts)
        start.value += posts.size
    }

}