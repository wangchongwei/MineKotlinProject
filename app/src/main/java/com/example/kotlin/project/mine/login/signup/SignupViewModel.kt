package com.example.kotlin.project.mine.login.signup

import android.text.Editable
import androidx.lifecycle.viewModelScope
import com.example.kotlin.project.mine.base.BaseViewModel
import com.example.kotlin.project.mine.login.UserRepository
import com.example.kotlin.project.mine.login.state.LoginIntent
import com.example.kotlin.project.mine.login.state.LoginState
import com.example.kotlin.project.mine.network.NetworkResponse
import com.example.kotlin.project.mine.utils.VerifyUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject


@HiltViewModel
class SignupViewModel @Inject constructor(val repository: UserRepository) : BaseViewModel() {

    private var loginName: String = ""
    private var loginPassword: String = ""
    private var repeatPassword: String = ""

    val userIntent = Channel<LoginIntent>(Channel.UNLIMITED)
    private val _state = MutableStateFlow<LoginState>(LoginState.Idle)
    val state: StateFlow<LoginState>
        get() = _state

    init {
        handleIntent()
    }


    private fun handleIntent() {
        viewModelScope.launch {
            userIntent.consumeAsFlow()
                .collect {
                    when(it) {
                        is LoginIntent.Login -> signup()
                    }
                }
        }
    }

    private fun signup() {
        if (VerifyUtils.isLoginNameValid(loginName)
            && VerifyUtils.isLoginPasswordValid(loginPassword)
            && VerifyUtils.isEqualsPassword(loginPassword, repeatPassword)
        ) {
            _state.value = LoginState.Loading
            println("账号密码等均合法")
            viewModelScope.launch (Dispatchers.IO) {
                repository.signup(loginName, loginPassword, repeatPassword)
                    .map {
                        _state.value = try {
                            LoginState.UserData(handleResponse(it))
                        } catch (e: Exception) {
                            e.printStackTrace()
                            LoginState.Error(NetworkResponse(null, 500, "${e.message}"))
                        }
                    }
                    .catch {
                        it.printStackTrace()
                    }
                    .collect{}

            }
        } else {
            println("账号或密码不符合格式!!")
        }
    }

    fun onLoginNameInputChange(editable: Editable?) {
        loginName = editable.toString()
    }

    fun onPasswordInputChange(editable: Editable?) {
        loginPassword = editable.toString()
    }

    fun onRepeatPasswordChange(editable: Editable?) {
        repeatPassword = editable.toString()
    }

}