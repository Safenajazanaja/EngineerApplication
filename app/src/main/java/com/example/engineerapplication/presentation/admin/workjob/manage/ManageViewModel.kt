package com.example.engineerapplication.presentation.admin.workjob.manage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.easyfix.data.datasource.DataSource
import com.example.engineerapplication.data.models.ManageModel
import com.example.engineerapplication.data.models.WorkaddTec2Model

class ManageViewModel:ViewModel() {


    private var _workjob = MutableLiveData<ManageModel>()
    val workjob: LiveData<ManageModel>
        get() = _workjob

    fun mana(idjob:Int){
        _workjob.value=DataSource.manage(idjob)
    }

}