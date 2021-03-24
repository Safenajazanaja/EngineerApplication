package com.example.engineerapplication.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.easyfix.data.datasource.DataSource
import com.example.engineerapplication.data.request.LoginRequest

class MainViewModel:ViewModel() {
    private var _id = MutableLiveData<Int>()
    val id: LiveData<Int>
        get() = _id

    fun chekrole(technician_id :Int ){
        val resulte=DataSource.checkrole(technician_id)
        _id.value=resulte.role


    }
}