package com.example.kotlin.project.mine.network

data class NetworkResponse<T>(var data: T?, var errorCode: Int = 0, var errorMsg: String = "")
