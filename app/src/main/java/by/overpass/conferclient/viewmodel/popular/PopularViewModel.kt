package by.overpass.conferclient.viewmodel.popular

import android.arch.lifecycle.ViewModel
import android.content.Context
import by.overpass.conferclient.repository.popular.PopularRepository
import by.overpass.conferclient.viewmodel.BaseFactory

class PopularViewModel(context: Context) : ViewModel() {

    private val popularRepository = PopularRepository(context)

    fun getPopularNoCache() = popularRepository.getPopularNoCache()

    fun getPopular() = popularRepository.getPopular()

    class Factory(context: Context) : BaseFactory(context) {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return PopularViewModel(context) as T
        }
    }

}