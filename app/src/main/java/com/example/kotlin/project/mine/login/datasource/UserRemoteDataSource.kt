package com.example.kotlin.project.mine.login.datasource

import com.example.kotlin.project.mine.login.UserInfo
import com.example.kotlin.project.mine.network.NetworkResponse
import com.example.kotlin.project.mine.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class UserRemoteDataSource : UserDataSource{


    override suspend fun login(loginName: String, password: String): Flow<NetworkResponse<UserInfo>> {
        return flow {
            emit(RetrofitClient.getApi().login(loginName, password))
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun signup(loginName: String, password: String, repassword: String): Flow<NetworkResponse<UserInfo>> {
        return flow {
            emit(RetrofitClient.getApi().signup(loginName, password, repassword))
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun logout(): Flow<Boolean> {
        return flow<Boolean> {
            RetrofitClient.getApi().logout()
            emit(true)
        }.flowOn(Dispatchers.IO)
    }
}