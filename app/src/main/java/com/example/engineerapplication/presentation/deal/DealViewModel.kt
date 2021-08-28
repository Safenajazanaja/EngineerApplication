package com.example.engineerapplication.presentation.deal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DealViewModel:ViewModel() {
    private var _toast = MutableLiveData<String>()
    val toast: LiveData<String>
        get() = _toast

}