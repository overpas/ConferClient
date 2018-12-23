package by.overpass.conferclient.data.dto

sealed class Status {
    object Loading : Status()
    data class Error(val message: String) : Status()
    object Success : Status()
}