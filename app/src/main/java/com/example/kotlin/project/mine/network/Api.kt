package com.example.kotlin.project.mine.network

import com.example.kotlin.project.mine.login.UserInfo
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface Api {

    @FormUrlEncoded
    @POST("/user/login")
    suspend fun login(@Field("username")userName: String, @Field("password")password: String): NetworkResponse<UserInfo>

    @FormUrlEncoded
    @POST("/user/register")
    suspend fun signup(@Field("username")userName: String, @Field("password")password: String, @Field("repassword")repassword: String): NetworkResponse<UserInfo>

    @GET("/user/logout/json")
    suspend fun logout()
}