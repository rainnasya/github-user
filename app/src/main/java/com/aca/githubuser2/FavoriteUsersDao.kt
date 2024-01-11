package com.aca.githubuser2

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoriteUsersDao {
    @Insert
    suspend fun addFavorite(favoriteUsers: FavoriteUsers)

    @Query("SELECT * FROM favorite_users")
    fun getFavoriteUsers(): LiveData<List<FavoriteUsers>>

    @Query("SELECT count(*) FROM favorite_users WHERE favorite_users.id = :id")
    suspend fun checkUsers(id:Int): Int

    @Query("DELETE FROM favorite_users WHERE favorite_users.id = :id")
    suspend fun removeFavorite(id: Int): Int


}