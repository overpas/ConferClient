package by.overpass.conferclient.data.callback

import android.arch.lifecycle.MutableLiveData
import by.overpass.conferclient.data.dto.PostCreationStatus
import by.overpass.conferclient.data.network.dto.PostTree
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class PostCreationCallback(
    private val postCreationStatus: MutableLiveData<PostCreationStatus>,
    private val defaultErrorMessage: String
) : Callback<PostTree> {

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

}