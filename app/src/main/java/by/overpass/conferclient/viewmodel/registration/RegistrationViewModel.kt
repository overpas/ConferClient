package by.overpass.conferclient.viewmodel.registration

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import by.overpass.conferclient.data.dto.RegistrationStatus
import by.overpass.conferclient.data.dto.UserRegistration
import by.overpass.conferclient.data.repository.register.RegistrationRepository
import by.overpass.conferclient.viewmodel.BaseFactory

class RegistrationViewModel(context: Context) : ViewModel() {

    private val registrationRepository =
        RegistrationRepository(context)

    fun register(userRegistration: UserRegistration): LiveData<RegistrationStatus> =
        registrationRepository.register(userRegistration)

    class Factory(context: Context) : BaseFactory(context) {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return RegistrationViewModel(context) as T
        }
    }

}