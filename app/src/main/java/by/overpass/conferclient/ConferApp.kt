package by.overpass.conferclient

import android.app.Application
import android.content.Context
import by.overpass.conferclient.util.Preferences
import timber.log.Timber

class ConferApp : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        Timber.plant(Timber.DebugTree())
        Preferences.deleteToken()
    }

    companion object {
        private lateinit var instance: ConferApp
        fun getAppContext(): Context = instance.applicationContext
    }

}