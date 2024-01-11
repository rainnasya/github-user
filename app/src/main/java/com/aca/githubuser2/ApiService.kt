package com.aca.githubuser2

import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getSearchUser(
        @Query("q") query : String
    ): Call<UsersResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getUserDetail(
        @Path("username") username : String
    ): Call<DetailUsersResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getFollowers(
        @Path("username") username : String
    ): Call<ArrayList<Users>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getFollowing(
        @Path("username") username : String
    ): Call<ArrayList<Users>>
}