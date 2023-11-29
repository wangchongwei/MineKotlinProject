package com.example.kotlin.project.mine.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import com.example.kotlin.project.mine.databinding.ActivityLoginBinding
import com.example.kotlin.project.mine.login.signup.SignupActivity
import com.example.kotlin.project.mine.login.state.LoginIntent
import com.example.kotlin.project.mine.login.state.LoginState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private val viewModel: LoginViewModel by viewModels()


    private var uiStateJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        initView()

        observeViewModelState()
    }

    private fun initView() {
        binding.btnSignup.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }

        binding.inputLoginName.addTextChangedListener {
            viewModel.onLoginNameInputChange(it)
        }

        binding.inputLoginPassword.addTextChangedListener {
            viewModel.onPasswordInputChange(it)
        }

        binding.btnLogin.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
                viewModel.userIntent.send(LoginIntent.Login)
            }
        }
    }

    private fun observeViewModelState() {
        uiStateJob = lifecycleScope.launchWhenStarted {
            viewModel.state.collect {
                when(it) {
                    is LoginState.Idle -> {
                        binding.progressBar.visibility = View.GONE
                    }

                    is LoginState.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is LoginState.UserData -> {
                        // 请求成功数据
                        binding.progressBar.visibility = View.GONE
                    }

                    is LoginState.Error -> {
                        binding.progressBar.visibility = View.GONE
                        println("error => ${it.error.errorCode},msg:${it.error.errorMsg}")
                    }

                }
            }
        }
    }


    override fun onStop() {
        uiStateJob?.cancel()
        super.onStop()
    }


}