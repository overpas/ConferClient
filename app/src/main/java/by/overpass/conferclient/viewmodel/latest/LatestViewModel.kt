package by.overpass.conferclient.viewmodel.latest

import android.arch.lifecycle.ViewModel
import android.content.Context
import by.overpass.conferclient.data.repository.latest.LatestRepository
import by.overpass.conferclient.viewmodel.BaseFactory

class LatestViewModel(context: Context) : ViewModel() {

    private val latestRepository =
        LatestRepository(context)

    fun getLatestPosts() = latestRepository.pagedPostData

    fun getProgress() = latestRepository.progress

    class Factory(context: Context) : BaseFactory(context) {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return LatestViewModel(context) as T
        }
    }

}