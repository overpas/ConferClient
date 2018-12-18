package by.overpass.conferclient.data.dto

sealed class PostCreationStatus {
    object Loading : PostCreationStatus()
    data class Success(val postId: Long) : PostCreationStatus()
    data class Error(val message: String) : PostCreationStatus()
}