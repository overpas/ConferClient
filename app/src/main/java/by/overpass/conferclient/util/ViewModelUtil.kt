package by.overpass.conferclient.util

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import by.overpass.conferclient.ConferApp
import by.overpass.conferclient.viewmodel.BaseFactory

inline fun <reified T : ViewModel> Fragment.vm(
    factoryClass: Class<out BaseFactory>
): Lazy<T> {
    return lazy {
        val factoryConstructor = factoryClass.getConstructor(Context::class.java)
        val context = this.context ?: ConferApp.getAppContext()
        ViewModelProviders.of(this, factoryConstructor.newInstance(context))
            .get(T::class.java)
    }
}

inline fun <reified T : ViewModel> Fragment.parentVm(
    factoryClass: Class<out BaseFactory>
): Lazy<T> {
    return lazy {
        val factoryConstructor = factoryClass.getConstructor(Context::class.java)
        ViewModelProviders.of(activity!!, factoryConstructor.newInstance(context!!))
            .get(T::class.java)
    }
}

inline fun <reified T : ViewModel> FragmentActivity.vm(
    factoryClass: Class<out BaseFactory>
): Lazy<T> {
    return lazy {
        val factoryConstructor = factoryClass.getConstructor(Context::class.java)
        ViewModelProviders.of(this, factoryConstructor.newInstance(this))
            .get(T::class.java)
    }
}