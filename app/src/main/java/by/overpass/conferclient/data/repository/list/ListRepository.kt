package by.overpass.conferclient.data.repository.list

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import by.overpass.conferclient.data.callback.CurrentUserCallback
import by.overpass.conferclient.data.callback.LoginCallback
import by.overpass.conferclient.data.db.ConferDatabase
import by.overpass.conferclient.data.db.entity.User
import by.overpass.conferclient.data.dto.AuthStatus
import by.overpass.conferclient.data.network.CLIENT
import by.overpass.conferclient.data.network.api.ConferApi
import by.overpass.conferclient.util.Preferences
import okhttp3.Credentials

private const val CLIENT_ID = "confer-client"
private const val CLIENT_SECRET = "confer-secret"

class ListRepository(context: Context) {

    private val conferApi = CLIENT.create(ConferApi::class.java)
    private val userDao = ConferDatabase.getInstance(context).getUserDao()

    fun login(username: String, password: String): LiveData<AuthStatus> {
        val authStatusData = MutableLiveData<AuthStatus>()
        authStatusData.value = AuthStatus.Loading
        conferApi.login(getBasicAuthHeader(), username, password)
            .enqueue(LoginCallback(authStatusData))
        return authStatusData
    }

    fun getCurrentUser(): LiveData<User> {
        val userData = MediatorLiveData<User>()
        Preferences.getToken()?.run {
            conferApi.getUserById(userId).enqueue(CurrentUserCallback(userId, userDao, userData))
        }
        return userData
    }

}

private fun getBasicAuthHeader() = Credentials.basic(
    CLIENT_ID,
    CLIENT_SECRET
)