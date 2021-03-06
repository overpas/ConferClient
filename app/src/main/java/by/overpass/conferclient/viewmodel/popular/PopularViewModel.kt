package by.overpass.conferclient.viewmodel.popular

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import by.overpass.conferclient.data.dto.PostWithUser
import by.overpass.conferclient.data.dto.Status
import by.overpass.conferclient.data.repository.popular.PopularRepository
import by.overpass.conferclient.viewmodel.BaseFactory

class PopularViewModel(context: Context) : ViewModel() {

    private val progress = MutableLiveData<Status>()
    private val popularRepository =
        PopularRepository(progress, context)

    fun getPopular(text: String?): LiveData<List<PostWithUser>> = if (text == null) {
        popularRepository.getPopular()
    } else {
        popularRepository.getPopularLocallyByText(text)
    }

    fun getProgress(): LiveData<Status> = progress

    class Factory(context: Context) : BaseFactory(context) {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return PopularViewModel(context) as T
        }
    }

}