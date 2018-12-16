package by.overpass.conferclient.util

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import by.overpass.conferclient.ConferApp
import by.overpass.conferclient.viewmodel.BaseFactory

private typealias VMFactory = ViewModelProvider.NewInstanceFactory

inline fun <reified T : ViewModel> getVm(ofWhat: FragmentActivity,
                                         vmClass: Class<T>,
                                         factoryClass: Class<out BaseFactory>): T {
    val factoryConstructor = factoryClass.getConstructor(Context::class.java)
    return ViewModelProviders.of(ofWhat, factoryConstructor.newInstance(ofWhat)).get(vmClass)
}

inline fun <reified T : ViewModel> getVm(ofWhat: Fragment,
                                         vmClass: Class<T>,
                                         factoryClass: Class<out BaseFactory>): T {
    val factoryConstructor = factoryClass.getConstructor(Context::class.java)
    val context = ofWhat.context ?: ConferApp.getAppContext()
    return ViewModelProviders.of(ofWhat, factoryConstructor.newInstance(context)).get(vmClass)
}