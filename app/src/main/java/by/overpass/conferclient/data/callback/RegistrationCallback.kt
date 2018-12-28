package by.overpass.conferclient.data.callback

import android.arch.lifecycle.MutableLiveData
import by.overpass.conferclient.data.dto.RegistrationStatus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class RegistrationCallback(
    private val registerData: MutableLiveData<RegistrationStatus>,
    private val defaultErrorMessage: String
) : Callback<Void> {

    override fun onFailure(call: Call<Void>, t: Throwable) {
        Timber.d(t)
        registerData.value = RegistrationStatus.Error(t.message ?: defaultErrorMessage)
    }

    override fun onResponse(call: Call<Void>, response: Response<Void>) {
        registerData.value = if (response.isSuccessful) {
            RegistrationStatus.Success
        } else {
            RegistrationStatus.Error(response.message())
        }
    }

}