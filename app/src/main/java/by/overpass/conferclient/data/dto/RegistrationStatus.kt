package by.overpass.conferclient.data.dto

sealed class RegistrationStatus {
    object Loading : RegistrationStatus()
    object Success : RegistrationStatus()
    data class Error(val message: String) : RegistrationStatus()
}