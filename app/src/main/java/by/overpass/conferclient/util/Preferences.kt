package by.overpass.conferclient.util

import android.content.SharedPreferences
import android.preference.PreferenceManager
import by.overpass.conferclient.ConferApp
import by.overpass.conferclient.data.network.dto.TokenResponse
import com.google.gson.Gson

object Preferences {

    private const val DEFAULT_STRING = ""
    private const val AUTH_TOKEN_KEY = "AUTH_TOKEN_KEY"

    private val gson = Gson()

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

    fun saveToken(tokenResponse: TokenResponse) {
        val tokenResponseJson = gson.toJson(tokenResponse)
        prefs.edit()
            .putString(AUTH_TOKEN_KEY, tokenResponseJson)
            .apply()
    }

    fun getToken(): TokenResponse? {
        val json = prefs.getString(AUTH_TOKEN_KEY, DEFAULT_STRING)
        return gson.fromJson(json, TokenResponse::class.java)
    }

    fun tokenExists(): Boolean = getToken() != null

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