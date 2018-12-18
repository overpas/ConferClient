package by.overpass.conferclient.data.dto

sealed class AuthStatus {
    object Loading : AuthStatus()
    data class LoggedIn(val token: String) : AuthStatus()
    data class Error(val message: String) : AuthStatus()
}