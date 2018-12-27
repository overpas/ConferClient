package by.overpass.conferclient.data.repository.posting

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import by.overpass.conferclient.data.dto.PostCreationStatus
import by.overpass.conferclient.data.network.CLIENT
import by.overpass.conferclient.data.network.api.ConferApi
import by.overpass.conferclient.data.network.dto.PostTree
import by.overpass.conferclient.util.Preferences
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import by.overpass.conferclient.R

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
                .enqueue(object : Callback<PostTree> {
                    override fun onFailure(call: Call<PostTree>, t: Throwable) {
                        Timber.e(t)
                        postCreationStatus.value =
                                PostCreationStatus.Error(t.message ?: defaultErrorMessage)
                    }

                    override fun onResponse(call: Call<PostTree>, response: Response<PostTree>) {
                        if (response.isSuccessful && response.body() != null) {
                            val createdPostId = response.body()!!.id
                            postCreationStatus.value = PostCreationStatus.Success(createdPostId)
                        }
                    }

                })
        } else {
            postCreationStatus.value = PostCreationStatus.Error(notAuthorizedMessage)
        }
        return postCreationStatus
    }

}