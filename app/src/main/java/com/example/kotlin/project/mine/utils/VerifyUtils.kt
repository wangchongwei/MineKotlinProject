package com.example.kotlin.project.mine.utils

object VerifyUtils {

    /**
     * 登陆用户名格式是否正确
     */
    fun isLoginNameValid(loginName: String): Boolean = loginName.length >= 5

    /**
     * 登陆密码格式是否正确
     */
    fun isLoginPasswordValid(password: String): Boolean = password.length >= 8

    /**
     * 判断两次密码是否一致
     */
    fun isEqualsPassword(password: String, repeatPassword: String): Boolean = password.equals(repeatPassword)
}