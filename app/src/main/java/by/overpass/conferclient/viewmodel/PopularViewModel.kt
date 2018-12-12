package by.overpass.conferclient.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import by.overpass.conferclient.data.dto.PostWithUserAndPopularity
import by.overpass.conferclient.data.network.pojo.Post
import by.overpass.conferclient.repository.PopularRepository

class PopularViewModel(context: Context) : ViewModel() {

    private val popularRepository = PopularRepository(context)

    fun getPopularNoCache(): LiveData<List<Post>> = popularRepository.getPopularNoCache()

    fun getPopular(): LiveData<List<PostWithUserAndPopularity>> = popularRepository.getPopular()

    class Factory(private val context: Context) : ViewModelProvider.NewInstanceFactory() {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return PopularViewModel(context) as T
        }

    }

}