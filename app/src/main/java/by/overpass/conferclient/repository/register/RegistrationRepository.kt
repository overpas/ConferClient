package by.overpass.conferclient.repository.register

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import by.overpass.conferclient.data.dto.RegistrationStatus
import by.overpass.conferclient.data.dto.UserRegistration
import by.overpass.conferclient.data.network.CLIENT
import by.overpass.conferclient.data.network.api.ConferApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class RegistrationRepository(context: Context) {

    private val conferApi = CLIENT.create(ConferApi::class.java)

    fun register(userRegistration: UserRegistration): LiveData<RegistrationStatus> {
        val registerData = MutableLiveData<RegistrationStatus>()
        registerData.value = RegistrationStatus.Loading
        conferApi.register(userRegistration).enqueue(object : Callback<Void> {
            override fun onFailure(call: Call<Void>, t: Throwable) {
                Timber.d(t)
                registerData.value = RegistrationStatus.Error(t.message ?: "Something went wrong")
            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                registerData.value = if (response.isSuccessful) {
                    RegistrationStatus.Success
                } else {
                    RegistrationStatus.Error(response.message())
                }
            }

        })
        return registerData
    }


}