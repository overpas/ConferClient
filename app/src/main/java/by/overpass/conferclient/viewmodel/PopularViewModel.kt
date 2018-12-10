package by.overpass.conferclient.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import by.overpass.conferclient.data.network.pojo.Post
import by.overpass.conferclient.repository.PopularRepository

class PopularViewModel : ViewModel() {

    private val popularRepository = PopularRepository()

    fun getPopular(): LiveData<List<Post>> = popularRepository.getPopular()

}