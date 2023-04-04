package com.rinoindraw.githubyangke3.api

import com.rinoindraw.githubyangke3.response.DetailResponse
import com.rinoindraw.githubyangke3.GithubUser
import com.rinoindraw.githubyangke3.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
        @Headers("Authorization: token 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")
    fun getUserList(
        @Query("q") id: String
    ): Call<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getUsersDetail(
        @Path("username") username: String
    ): Call<DetailResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getUsersFollowers(
        @Path("username") username: String
    ): Call<List<GithubUser>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getUsersFollowing(
        @Path("username") username: String
    ): Call<List<GithubUser>>

}