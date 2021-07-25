package com.example.engineerapplication.presentation.technician.table.detail


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.easyfix.data.datasource.DataSource
import com.example.engineerapplication.data.models.ImagModel
import com.example.engineerapplication.data.models.ListModel


class DetailViewModel : ViewModel() {

    private var _list = MutableLiveData<List<ListModel>>()
    val list: LiveData<List<ListModel>>
        get() = _list
    private val _imgpayModel = MutableLiveData<ImagModel>()
    val imgpayModel: LiveData<ImagModel>
        get() = _imgpayModel


    fun listdetail(request: Int) {
        val result = DataSource.listitem(request)
        _list.value = result
    }

    fun chekImg(jobid: Int) {
        _imgpayModel.value = DataSource.chekImage(jobid)

    }



    companion object {
        private const val TAG = "www"
    }
}