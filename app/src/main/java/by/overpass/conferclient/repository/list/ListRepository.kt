package by.overpass.conferclient.repository.list

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.content.Context
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

    fun newPost(): LiveData<PostCreationStatus> {
        // TODO: Create post
        val newPostData = MutableLiveData<PostCreationStatus>()
        newPostData.value = PostCreationStatus.Loading
        runInBackground {
            Thread.sleep(1500)
            // TODO: If failed -> value = PostCreationStatus.Error(message)
            runOnUI {
                // TODO: Stub
                newPostData.value = PostCreationStatus.Success(1)
            }
        }
        return newPostData
    }

}

private fun getBasicAuthHeader() = Credentials.basic(CLIENT_ID, CLIENT_SECRET)