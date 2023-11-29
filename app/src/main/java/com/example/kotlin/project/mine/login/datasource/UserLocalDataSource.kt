package com.example.kotlin.project.mine.login.datasource

import com.example.kotlin.project.mine.login.UserInfo
import com.example.kotlin.project.mine.network.NetworkResponse
import kotlinx.coroutines.flow.Flow

class UserLocalDataSource : UserDataSource {
    override suspend fun login(loginName: String, password: String): Flow<NetworkResponse<UserInfo>> {
        TODO("Not yet implemented")
    }

    override suspend fun signup(loginName: String, password: String, repassword: String): Flow<NetworkResponse<UserInfo>> {
        TODO("Not yet implemented")
    }

    override suspend fun logout(): Flow<Boolean> {
        TODO("Not yet implemented")
    }
}