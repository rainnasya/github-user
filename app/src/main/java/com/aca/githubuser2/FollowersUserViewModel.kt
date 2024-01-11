package com.aca.githubuser2

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersUserViewModel : ViewModel(){
    val listFollowers = MutableLiveData<ArrayList<Users>>()

    fun setListFollowers(username: String){
        RetrofitClientUsers.apiInstance
            .getFollowers(username)
            .enqueue(object : Callback<ArrayList<Users>>{
                override fun onResponse(
                    call: Call<ArrayList<Users>>,
                    response: Response<ArrayList<Users>>
                ) {
                    if (response.isSuccessful){
                        listFollowers.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ArrayList<Users>>, t: Throwable) {
                    Log.d("onFailure", t.message.toString())
                }

            })
    }

    fun getListFollowers(): LiveData<ArrayList<Users>>{
        return listFollowers
    }
}