package by.overpass.conferclient.data.repository.list

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import by.overpass.conferclient.ConferApp
import by.overpass.conferclient.data.db.ConferDatabase
import by.overpass.conferclient.data.db.entity.GUEST
import by.overpass.conferclient.data.db.entity.User
import by.overpass.conferclient.data.dto.AuthStatus
import by.overpass.conferclient.data.dto.PostCreationStatus
import by.overpass.conferclient.data.network.CLIENT
import by.overpass.conferclient.data.network.api.ConferApi
import by.overpass.conferclient.data.network.dto.TokenResponse
import by.overpass.conferclient.util.Preferences
import by.overpass.conferclient.util.runInBackground
import by.overpass.conferclient.util.runOnUI
import okhttp3.Credentials
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

private const val CLIENT_ID = "confer-client"
private const val CLIENT_SECRET = "confer-secret"

class ListRepository(context: Context) {

    private val conferApi = CLIENT.create(ConferApi::class.java)
    private val userDao = ConferDatabase.getInstance(context).getUserDao()

    fun login(username: String, password: String): LiveData<AuthStatus> {
        val authStatusData = MutableLiveData<AuthStatus>()
        authStatusData.value = AuthStatus.Loading
        conferApi.login(getBasicAuthHeader(), username, password)
            .enqueue(object : Callback<TokenResponse> {
                override fun onFailure(call: Call<TokenResponse>, t: Throwable) {
                    Timber.e(t)
                    t.message?.run {
                        authStatusData.value = AuthStatus.Error(this)
                    }
                }

                override fun onResponse(
                    call: Call<TokenResponse>,
                    response: Response<TokenResponse>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        val tokenResponse = response.body()!!
                        authStatusData.value = AuthStatus.LoggedIn(tokenResponse.accessToken)
                        runInBackground {
                            Preferences.saveToken(tokenResponse)
                        }
                    } else {
                        authStatusData.value = AuthStatus.Error(response.message())
                    }
                }

            })
        return authStatusData
    }

    fun getCurrentUser(): LiveData<User> {
        val userData = MutableLiveData<User>()
        Preferences.getToken()?.run {
            conferApi.getUserById(userId).enqueue(object : Callback<User> {
                override fun onFailure(call: Call<User>, t: Throwable) {
                    Timber.e(t)
                }

                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if (response.isSuccessful && response.body() != null) {
                        userData.value = response.body()!!
                    }
                }

            })
        }
        return userData
    }

}

private fun getBasicAuthHeader() = Credentials.basic(
    CLIENT_ID,
    CLIENT_SECRET
)