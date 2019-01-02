package by.overpass.conferclient.data.callback

import android.arch.lifecycle.MutableLiveData
import by.overpass.conferclient.data.dto.AuthStatus
import by.overpass.conferclient.data.network.dto.TokenResponse
import by.overpass.conferclient.util.Preferences
import by.overpass.conferclient.util.runInBackground
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class LoginCallback(
    private val authStatusData: MutableLiveData<AuthStatus>,
    private val defaultErrorMessage: String
) : Callback<TokenResponse> {

    override fun onFailure(call: Call<TokenResponse>, t: Throwable) {
        Timber.e(t)
        authStatusData.value = AuthStatus.Error(defaultErrorMessage)
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
            authStatusData.value = AuthStatus.Error(defaultErrorMessage)
        }
    }

}