package by.overpass.conferclient.util

import android.content.SharedPreferences
import android.preference.PreferenceManager
import by.overpass.conferclient.ConferApp

object Preferences {

    private const val DEFAULT_STRING = ""
    private const val AUTH_TOKEN_KEY = "AUTH_TOKEN_KEY"

    private val tokenChangedListeners = mutableListOf<OnTokenChangedListener>()

    private val prefs = PreferenceManager.getDefaultSharedPreferences(ConferApp.getAppContext())

    private val listener = SharedPreferences.OnSharedPreferenceChangeListener { prefs, key ->
        if (key == AUTH_TOKEN_KEY) {
            tokenChangedListeners.forEach(OnTokenChangedListener::onTokenChanged)
        }
    }

    init {
        prefs.registerOnSharedPreferenceChangeListener(listener)
    }

    fun saveToken(token: String) {
        prefs.edit()
            .putString(AUTH_TOKEN_KEY, token)
            .apply()
    }

    fun getToken(): String? = prefs.getString(AUTH_TOKEN_KEY, DEFAULT_STRING)

    fun tokenExists(): Boolean = !getToken().isNullOrEmpty()

    fun deleteToken() {
        prefs.edit()
            .remove(AUTH_TOKEN_KEY)
            .apply()
    }

    fun addTokenListener(tokenChangedListener: OnTokenChangedListener) {
        if (!tokenChangedListeners.contains(tokenChangedListener)) {
            tokenChangedListeners.add(tokenChangedListener)
        }
    }

    fun removeTokenListener(tokenChangedListener: OnTokenChangedListener) {
        if (tokenChangedListeners.contains(tokenChangedListener)) {
            tokenChangedListeners.remove(tokenChangedListener)
        }
    }

    interface OnTokenChangedListener {
        fun onTokenChanged()
    }

}