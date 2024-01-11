package com.aca.githubuser2

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database (
    entities = [FavoriteUsers::class],
    version = 1
)
abstract class UsersDatabase: RoomDatabase() {
    companion object{
        var INSTANCE : UsersDatabase? = null

        fun getDatabase(context: Context): UsersDatabase?{
            if(INSTANCE==null){
                synchronized(UsersDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext, UsersDatabase::class.java,"users_database").build()
                }
            }
            return INSTANCE
        }
    }

    abstract fun favoriteUsersDao(): FavoriteUsersDao

}