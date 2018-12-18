package by.overpass.conferclient.data.dto

data class UserRegistration(
    val username: String,
    val fullName: String,
    val password: String,
    val confirmPassword: String,
    val email: String
)