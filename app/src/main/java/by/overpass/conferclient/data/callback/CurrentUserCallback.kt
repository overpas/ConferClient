package by.overpass.conferclient.data.callback

import android.arch.lifecycle.MediatorLiveData
import by.overpass.conferclient.data.db.dao.UserDao
import by.overpass.conferclient.data.db.entity.User
import by.overpass.conferclient.util.runInBackground
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class CurrentUserCallback(
    private val userId: Long,
    private val userDao: UserDao,
    private val userData: MediatorLiveData<User>
) : Callback<User> {

    override fun onFailure(call: Call<User>, t: Throwable) {
        Timber.e(t)
    }

    override fun onResponse(call: Call<User>, response: Response<User>) {
        if (response.isSuccessful && response.body() != null) {
            val user = response.body()!!
            runInBackground {
                userDao.insert(user)
            }
            userData.addSource(userDao.findById(userId)) {
                it?.run {
                    userData.value = this
                }
            }
        }
    }

}