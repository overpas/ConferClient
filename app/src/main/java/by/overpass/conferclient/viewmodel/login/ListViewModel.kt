package by.overpass.conferclient.viewmodel.login

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import by.overpass.conferclient.data.dto.AuthStatus
import by.overpass.conferclient.data.dto.PostCreationStatus
import by.overpass.conferclient.repository.list.ListRepository
import by.overpass.conferclient.util.Preferences
import by.overpass.conferclient.viewmodel.BaseFactory

class ListViewModel(context: Context) : ViewModel() {

    private val listRepository = ListRepository(context)

    fun login(/*TODO: User credentials or existing token*/): LiveData<AuthStatus> {
        return listRepository.login(/*TODO: Pass data*/)
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