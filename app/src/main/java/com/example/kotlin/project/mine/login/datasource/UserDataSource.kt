package com.example.kotlin.project.mine.login.datasource

import com.example.kotlin.project.mine.login.UserInfo
import com.example.kotlin.project.mine.network.NetworkResponse
import kotlinx.coroutines.flow.Flow

interface UserDataSource {


    suspend fun login(loginName: String, password: String): Flow<NetworkResponse<UserInfo>>

    suspend fun signup(loginName: String, password: String, repassword: String): Flow<NetworkResponse<UserInfo>>

    suspend fun logout(): Flow<Boolean>

}