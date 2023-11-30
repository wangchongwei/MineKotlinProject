package com.example.kotlin.project.mine.login.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import com.example.kotlin.project.mine.MainActivity
import com.example.kotlin.project.mine.R
import com.example.kotlin.project.mine.databinding.ActivitySignupBinding
import com.example.kotlin.project.mine.login.state.LoginIntent
import com.example.kotlin.project.mine.login.state.LoginState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private val viewModel: SignupViewModel by viewModels()

    private var uiStateJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()

        observeViewModelState()
    }



    private fun initView() {
        binding.inputLoginName.addTextChangedListener {
            viewModel.onLoginNameInputChange(it)
        }
        binding.inputLoginPassword.addTextChangedListener {
            viewModel.onPasswordInputChange(it)
        }
        binding.inputLoginPasswordAgain.addTextChangedListener {
            viewModel.onRepeatPasswordChange(it)
        }

        binding.btnSignup.setOnClickListener {
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
                        println("==== idle ====")
                        binding.progressBar.visibility = View.GONE
                    }

                    is LoginState.Loading -> {
                        println("==== Loading ====")
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is LoginState.UserData -> {
                        println("==== Success ====")
                        // 请求成功数据
                        binding.progressBar.visibility = View.GONE
                        startActivity(Intent(this@SignupActivity, MainActivity::class.java))
                    }

                    is LoginState.Error -> {
                        println("==== Error ====")
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