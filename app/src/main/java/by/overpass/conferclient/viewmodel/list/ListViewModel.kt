package by.overpass.conferclient.viewmodel.list

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import by.overpass.conferclient.data.dto.AuthStatus
import by.overpass.conferclient.repository.list.ListRepository
import by.overpass.conferclient.viewmodel.BaseFactory
import by.overpass.conferclient.viewmodel.base.PostingViewModel

class ListViewModel(context: Context) : PostingViewModel(context) {

    private val listRepository = ListRepository(context)

    fun login(username: String, password: String): LiveData<AuthStatus> {
        return listRepository.login(username, password)
    }

    class Factory(context: Context) : BaseFactory(context) {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ListViewModel(context) as T
        }
    }

}