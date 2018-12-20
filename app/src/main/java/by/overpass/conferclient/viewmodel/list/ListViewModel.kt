package by.overpass.conferclient.viewmodel.list

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import by.overpass.conferclient.data.dto.AuthStatus
import by.overpass.conferclient.data.dto.PostCreationStatus
import by.overpass.conferclient.repository.list.ListRepository
import by.overpass.conferclient.viewmodel.BaseFactory

class ListViewModel(context: Context) : ViewModel() {

    private val listRepository = ListRepository(context)

    fun login(username: String, password: String): LiveData<AuthStatus> {
        return listRepository.login(username, password)
    }

    fun newPost(/*TODO: Post data*/): LiveData<PostCreationStatus> {
        return listRepository.newPost(/*TODO: Pass data*/)
    }

    class Factory(context: Context) : BaseFactory(context) {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ListViewModel(context) as T
        }
    }

}