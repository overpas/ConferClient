package by.overpass.conferclient.viewmodel.base

import android.arch.lifecycle.ViewModel
import android.content.Context
import by.overpass.conferclient.repository.posting.PostingRepository
import by.overpass.conferclient.viewmodel.BaseFactory

open class PostingViewModel(context: Context) : ViewModel() {

    private val postingRepository = PostingRepository(context)

    fun newPost(title: String, body: String, inReplyTo: Long) =
        postingRepository.createNewPost(title, body, inReplyTo)

    class Factory(context: Context) : BaseFactory(context) {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return PostingViewModel(context) as T
        }
    }

}