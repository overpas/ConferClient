package by.overpass.conferclient.repository.list

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import by.overpass.conferclient.data.dto.AuthStatus
import by.overpass.conferclient.data.dto.PostCreationStatus
import by.overpass.conferclient.data.network.CLIENT
import by.overpass.conferclient.data.network.api.ConferApi
import by.overpass.conferclient.util.runInBackground
import by.overpass.conferclient.util.runOnUI

class ListRepository(context: Context) {

    private val conferApi = CLIENT.create(ConferApi::class.java)

    fun login(): LiveData<AuthStatus> {
        // TODO: Use simple get/refresh auth token
        val authStatusData = MutableLiveData<AuthStatus>()
        authStatusData.value = AuthStatus.Loading
        runInBackground {
            Thread.sleep(3000)
            // TODO: If failed -> value = AuthStatus.Error(message)
            runOnUI {
                // TODO: Stub
                authStatusData.value = AuthStatus.LoggedIn("123")
            }
        }
        return authStatusData
    }

    fun newPost(): LiveData<PostCreationStatus> {
        // TODO: Create post
        val newPostData = MutableLiveData<PostCreationStatus>()
        newPostData.value = PostCreationStatus.Loading
        runInBackground {
            Thread.sleep(1500)
            // TODO: If failed -> value = PostCreationStatus.Error(message)
            runOnUI {
                // TODO: Stub
                newPostData.value = PostCreationStatus.Success(1)
            }
        }
        return newPostData
    }

}