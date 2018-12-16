package by.overpass.conferclient.viewmodel.latest

import android.arch.lifecycle.ViewModel
import android.content.Context
import by.overpass.conferclient.viewmodel.BaseFactory

class LatestViewModel(context: Context) : ViewModel() {

    // TODO: Implement

    class Factory(context: Context) : BaseFactory(context) {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return LatestViewModel(context) as T
        }
    }

}