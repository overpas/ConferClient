package by.overpass.conferclient.data.dto

data class UserRegistration(
    var username: String,
    var fullName: String,
    var password: String,
    var confirmPassword: String,
    var email: String
)