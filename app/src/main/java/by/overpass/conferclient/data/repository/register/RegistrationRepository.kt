package by.overpass.conferclient.data.repository.register

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import by.overpass.conferclient.R
import by.overpass.conferclient.data.callback.RegistrationCallback
import by.overpass.conferclient.data.dto.RegistrationStatus
import by.overpass.conferclient.data.dto.UserRegistration
import by.overpass.conferclient.data.network.CLIENT
import by.overpass.conferclient.data.network.api.ConferApi

class RegistrationRepository(context: Context) {

    private val conferApi = CLIENT.create(ConferApi::class.java)
    private val defaultErrorMessage = context.getString(R.string.default_error_message)

    fun register(userRegistration: UserRegistration): LiveData<RegistrationStatus> {
        val registerData = MutableLiveData<RegistrationStatus>()
        registerData.value = RegistrationStatus.Loading
        conferApi.register(userRegistration)
            .enqueue(RegistrationCallback(registerData, defaultErrorMessage))
        return registerData
    }


}