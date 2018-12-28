package by.overpass.conferclient.data.repository.posting

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import by.overpass.conferclient.R
import by.overpass.conferclient.data.callback.PostCreationCallback
import by.overpass.conferclient.data.dto.PostCreationStatus
import by.overpass.conferclient.data.network.CLIENT
import by.overpass.conferclient.data.network.api.ConferApi
import by.overpass.conferclient.util.Preferences

class PostingRepository(context: Context) {

    private val defaultErrorMessage = context.getString(R.string.default_error_message)
    private val notAuthorizedMessage = context.getString(R.string.not_authorized)

    private val conferApi = CLIENT.create(ConferApi::class.java)

    fun createNewPost(
        title: String,
        body: String,
        inReplyTo: Long
    ): LiveData<PostCreationStatus> {
        val postCreationStatus = MutableLiveData<PostCreationStatus>()
        postCreationStatus.value = PostCreationStatus.Loading
        val tokenResponse = Preferences.getToken()
        if (tokenResponse != null) {
            conferApi
                .createNewPost(
                    title,
                    body,
                    tokenResponse.userId,
                    inReplyTo,
                    tokenResponse.accessToken
                )
                .enqueue(PostCreationCallback(postCreationStatus, defaultErrorMessage))
        } else {
            postCreationStatus.value = PostCreationStatus.Error(notAuthorizedMessage)
        }
        return postCreationStatus
    }

}