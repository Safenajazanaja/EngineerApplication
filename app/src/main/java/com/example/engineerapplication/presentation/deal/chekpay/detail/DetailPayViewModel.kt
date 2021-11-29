package com.example.engineerapplication.presentation.deal.chekpay.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.easyfix.data.datasource.DataSource
import com.example.engineerapplication.data.models.DetailModel
import com.example.engineerapplication.data.models.ImagModel
import com.example.engineerapplication.data.models.ManageModel
import com.example.engineerapplication.data.models.Technician1Model
import com.example.engineerapplication.data.request.ChekTec2
import com.example.engineerapplication.data.request.ChekTecaddRequest
import com.example.engineerapplication.data.request.ConfimtecRequest
import com.example.engineerapplication.data.request.UpdatePayRequest

class DetailPayViewModel:ViewModel() {

    private var _workjob = MutableLiveData<DetailModel>()
    val workjob: LiveData<DetailModel>
        get() = _workjob
    private val _imgpayModel = MutableLiveData<ImagModel>()
    val imgpayModel: LiveData<ImagModel>
        get() = _imgpayModel

    fun mana(idjob: Int) {
        _workjob.value = DataSource.detail(idjob)
    }
    fun chekImg(jobid: Int) {
        _imgpayModel.value = DataSource.chekImage(jobid)

    }
    fun updatepay(req: UpdatePayRequest) {
      DataSource.updatepay(req)
    }



}