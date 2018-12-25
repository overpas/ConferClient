package by.overpass.conferclient.viewmodel.post

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import by.overpass.conferclient.ConferApp
import by.overpass.conferclient.data.dto.AuthStatus
import by.overpass.conferclient.data.dto.Status
import by.overpass.conferclient.repository.list.ListRepository
import by.overpass.conferclient.repository.post.PostRepository
import by.overpass.conferclient.repository.posting.PostingRepository
import by.overpass.conferclient.viewmodel.BaseFactory

open class PostingViewModel(context: Context = ConferApp.getAppContext()) : ViewModel() {

    val updates = MutableLiveData<Boolean>()

    private val progress = MutableLiveData<Status>()

    private val postingRepository = PostingRepository(context)
    private val listRepository = ListRepository(context)
    private val postRepository = PostRepository(progress, context)

    fun getPostTreeById(id: Long) = postRepository.getPostTreeById(id)

    fun getProgress(): LiveData<Status> = progress

    fun newPost(title: String, body: String, inReplyTo: Long) =
        postingRepository.createNewPost(title, body, inReplyTo)

    fun update() {
        updates.value = true
    }

    fun login(username: String, password: String): LiveData<AuthStatus> {
        return listRepository.login(username, password)
    }

    open class Factory(context: Context) : BaseFactory(context) {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return PostingViewModel(context) as T
        }
    }

}