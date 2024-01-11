package com.aca.githubuser2

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUsersViewModel(application: Application) : AndroidViewModel(application) {
    val user = MutableLiveData<DetailUsersResponse>()

    private var usersDao: FavoriteUsersDao?
    private var usersDb: UsersDatabase?

    init {
        usersDb = UsersDatabase.getDatabase(application)
        usersDao = usersDb?.favoriteUsersDao()
    }

    fun setUserDetail(username: String) {
        RetrofitClientUsers.apiInstance
            .getUserDetail(username)
            .enqueue(object : Callback<DetailUsersResponse> {
                override fun onResponse(
                    call: Call<DetailUsersResponse>,
                    response: Response<DetailUsersResponse>
                ) {
                    if (response.isSuccessful) {
                        user.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<DetailUsersResponse>, t: Throwable) {
                    Log.d("onFailure", t.message.toString())
                }

            })
    }

    fun getUserDetail(): LiveData<DetailUsersResponse> {
        return user
    }

    fun addFavorite(username: String, id: Int, avatarUrl: String, htmlUrl: String) {
        CoroutineScope(Dispatchers.IO).launch {
            var users = FavoriteUsers(
                username,
                id,
                avatarUrl,
                htmlUrl
            )
            usersDao?.addFavorite(users)
        }
    }

    suspend fun checkUsers(id: Int) = usersDao?.checkUsers(id)

    fun removeFavorite(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            usersDao?.removeFavorite(id)
        }
    }

}