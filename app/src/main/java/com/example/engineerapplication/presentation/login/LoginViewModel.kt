package com.example.engineerapplication.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.easyfix.data.datasource.DataSource
import com.example.engineerapplication.data.request.LoginRequest
import com.example.engineerapplication.data.response.LoginResponse

class LoginViewModel:ViewModel() {
    private var _toast = MutableLiveData<String>()
    val toast: LiveData<String>
        get() = _toast
    private var _login = MutableLiveData<Boolean>()
    val login: LiveData<Boolean>
        get() = _login

    private var _id = MutableLiveData<Int>()
    val id: LiveData<Int>
        get() = _id


    fun login(request: LoginRequest): LoginResponse {
        val response=LoginResponse()

        when {
            request.username.isBlank() -> _toast.value = "Username blank"
            request.password.isBlank() -> _toast.value = "Password blank"
            request.username.length < 4 -> _toast.value = "Username <4"
            request.password.length < 4 -> _toast.value = "Password <4"
            else -> {

                val result = DataSource.login(request)
                _login.value = result.success
                if (result.userId!=null){
                    _id.value=result.userId
                }



            }

        }
        return response

    }
}