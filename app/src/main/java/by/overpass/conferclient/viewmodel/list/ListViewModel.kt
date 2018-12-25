package by.overpass.conferclient.viewmodel.list

import android.arch.lifecycle.ViewModel
import android.content.Context
import by.overpass.conferclient.repository.list.ListRepository
import by.overpass.conferclient.viewmodel.BaseFactory

class ListViewModel(context: Context) : ViewModel() {

    private val repository = ListRepository(context)

    fun getCurrentUser() = repository.getCurrentUser()

    class Factory(context: Context) : BaseFactory(context) {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ListViewModel(context) as T
        }
    }

}