package com.example.kotlin.project.mine.base

import androidx.lifecycle.ViewModel
import com.example.kotlin.project.mine.network.NetworkResponse

open class BaseViewModel : ViewModel() {


    fun<T> handleResponse(response: NetworkResponse<T>): T {
        if(response.errorCode == 0) {
            return response.data !!
        }
        throw Exception("Network Error!")
    }
}