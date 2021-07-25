package com.example.engineerapplication.presentation.technician.table

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.easyfix.data.datasource.DataSource
import com.example.engineerapplication.data.models.OrderdetailModel

class TableViewModel:ViewModel() {

    private var _toast = MutableLiveData<String>()
    val toast: LiveData<String>
        get() = _toast

    private var _tablejob = MutableLiveData<List<OrderdetailModel>>()
    val tablejob: LiveData<List<OrderdetailModel>>
        get() = _tablejob

    fun tablejob(id:Int){

        val result=DataSource.tablejob(id)
        _tablejob.value=result

    }

}