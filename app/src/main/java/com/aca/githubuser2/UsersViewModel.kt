package com.aca.githubuser2

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UsersViewModel: ViewModel() {
    val listUsers = MutableLiveData<ArrayList<Users>>()

    fun setSearchUser(query: String){
        RetrofitClientUsers.apiInstance
            .getSearchUser(query)
            .enqueue(object :Callback<UsersResponse>{
                override fun onResponse(
                    call: Call<UsersResponse>,
                    response: Response<UsersResponse>
                ) {
                    if(response.isSuccessful){
                        listUsers.postValue(response.body()?.items)
                    }
                }

                override fun onFailure(call: Call<UsersResponse>, t: Throwable) {
                    Log.d("onFailure", t.message.toString())
                }

            })
    }

    fun getSearchUser(): LiveData<ArrayList<Users>>{
        return listUsers
    }
}
