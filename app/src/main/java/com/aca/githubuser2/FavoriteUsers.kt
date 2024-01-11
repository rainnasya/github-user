package com.aca.githubuser2

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "favorite_users")
data class FavoriteUsers(
    val login: String,
    @PrimaryKey
    val id: Int,
    val avatar_url : String,
    val html_url: String
): Serializable
