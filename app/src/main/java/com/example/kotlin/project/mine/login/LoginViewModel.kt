package com.example.kotlin.project.mine.login

import android.text.Editable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlin.project.mine.base.BaseViewModel
import com.example.kotlin.project.mine.login.state.LoginIntent
import com.example.kotlin.project.mine.login.state.LoginState
import com.example.kotlin.project.mine.network.NetworkResponse
import com.example.kotlin.project.mine.utils.VerifyUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
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
class LoginViewModel @Inject constructor (val repository: UserRepository) : BaseViewModel() {

    private var loginName: String = ""
    private var loginPassword: String = ""

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
                        is LoginIntent.Login -> login()
                    }
                }
        }
    }




    fun onLoginNameInputChange(editable: Editable?) {
        loginName = editable.toString()
    }

    fun onPasswordInputChange(editable: Editable?) {
        loginPassword = editable.toString()
    }


    fun login() {
        if (VerifyUtils.isLoginNameValid(loginName) && VerifyUtils.isLoginPasswordValid(loginPassword)) {
            viewModelScope.launch(Dispatchers.IO) {
                repository.login(loginName, loginPassword)
                    .map {
                        _state.value = try {
                            println("network success!!! ${it.data?.id}")
                            LoginState.UserData(handleResponse(it))
                        } catch (err: Exception) {
                            err.printStackTrace()
                            LoginState.Error(it)
                        }
                    }
                    .catch {
                        _state.value = LoginState.Error(NetworkResponse(data = null, errorCode = 500, errorMsg = "error: ${it.message}"))
                    }
                    .collect {  }

            }
        } else {
            println("===== 数据格式不合法 =====");
        }
    }
}