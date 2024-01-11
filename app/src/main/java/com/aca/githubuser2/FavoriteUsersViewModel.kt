package com.aca.githubuser2

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class FavoriteUsersViewModel(application: Application): AndroidViewModel(application) {

    private var usersDao: FavoriteUsersDao?
    private var usersDb: UsersDatabase?

    init {
        usersDb = UsersDatabase.getDatabase(application)
        usersDao = usersDb?.favoriteUsersDao()
    }

    fun getFavoriteUsers(): LiveData<List<FavoriteUsers>>? {
        return usersDao?.getFavoriteUsers()
    }

}