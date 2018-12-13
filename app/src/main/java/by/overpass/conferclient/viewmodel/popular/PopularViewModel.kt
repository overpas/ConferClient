package by.overpass.conferclient.viewmodel.popular

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import by.overpass.conferclient.repository.popular.PopularRepository

class PopularViewModel(context: Context) : ViewModel() {

    private val popularRepository = PopularRepository(context)

    fun getPopularNoCache() = popularRepository.getPopularNoCache()

    fun getPopular() = popularRepository.getPopular()

    class Factory(private val context: Context) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return PopularViewModel(context) as T
        }
    }

}