package by.overpass.conferclient.viewmodel

import android.arch.lifecycle.ViewModelProvider
import android.content.Context

open class BaseFactory(protected val context: Context) : ViewModelProvider.NewInstanceFactory()