package by.overpass.conferclient

import android.app.Application
import android.content.Context
import timber.log.Timber

class ConferApp : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        Timber.plant(Timber.DebugTree())
    }

    companion object {
        private lateinit var instance: ConferApp

        fun getAppContext(): Context = instance.applicationContext
    }

}