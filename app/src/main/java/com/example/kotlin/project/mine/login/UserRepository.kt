package com.example.kotlin.project.mine.login

import androidx.core.app.ActivityCompat
import com.example.kotlin.project.mine.login.datasource.UserDataSource
import com.example.kotlin.project.mine.login.datasource.UserRemoteDataSource
import com.example.kotlin.project.mine.network.NetworkResponse
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepository @Inject constructor() {

    private val userDataSource = UserRemoteDataSource()

    suspend fun login(loginName: String, password: String) : Flow<NetworkResponse<UserInfo>> {
        return userDataSource.login(loginName, password)
    }

    suspend fun signup(loginName: String, password: String, repassword: String) : Flow<NetworkResponse<UserInfo>> {
        return userDataSource.signup(loginName, password, repassword)
    }

    suspend fun logout() : Flow<Boolean> {
        return userDataSource.logout()
    }

}





@Module
@InstallIn(ActivityComponent::class)
object UserRepositoryModule {

    @Provides
    fun providerUserRepository() =  UserRepository()

//    fun providerUserDataSource() = UserRemoteDataSource()
}