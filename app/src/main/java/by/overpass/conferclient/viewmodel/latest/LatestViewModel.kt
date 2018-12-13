package by.overpass.conferclient.viewmodel.latest

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.content.Context

class LatestViewModel(context: Context) : ViewModel() {

    // TODO: Implement

    class Factory(private val context: Context) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return LatestViewModel(context) as T
        }
    }

}