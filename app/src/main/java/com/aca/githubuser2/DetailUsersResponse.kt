package com.aca.githubuser2

data class DetailUsersResponse(
    val login : String,
    val id : Int,
    val avatar_url : String,
    val followers_url : String,
    val following_url : String,
    val name : String,
    val following : Int,
    val followers : Int,
    val location : String,
    val public_repos : Int,
    val company : String
)
