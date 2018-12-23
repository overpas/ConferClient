package by.overpass.conferclient.viewmodel.post

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import by.overpass.conferclient.data.dto.Status
import by.overpass.conferclient.repository.post.PostRepository
import by.overpass.conferclient.viewmodel.BaseFactory

class PostViewModel(context: Context) : ViewModel() {

    private val progress = MutableLiveData<Status>()
    private val postRepository = PostRepository(progress, context)

    fun getPostTreeById(id: Long) = postRepository.getPostTreeById(id)

    fun getProgress(): LiveData<Status> = progress

    class Factory(context: Context) : BaseFactory(context) {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return PostViewModel(context) as T
        }
    }

}
