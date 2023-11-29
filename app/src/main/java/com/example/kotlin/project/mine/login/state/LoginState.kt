package com.example.kotlin.project.mine.login.state

import com.example.kotlin.project.mine.login.UserInfo
import com.example.kotlin.project.mine.network.NetworkResponse

/**
 * 登陆状态
 */
sealed class LoginState {

    object Idle : LoginState()

    object Loading : LoginState()

    data class UserData(val user: UserInfo) : LoginState()

    data class Error(val error: NetworkResponse<UserInfo>) : LoginState()

}
