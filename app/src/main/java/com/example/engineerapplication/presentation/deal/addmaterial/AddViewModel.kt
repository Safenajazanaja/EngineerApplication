package com.example.engineerapplication.presentation.deal.addmaterial

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.easyfix.data.datasource.DataSource
import com.example.engineerapplication.data.models.ListMaterialModel
import com.example.engineerapplication.data.request.AddMaterialRequest
import com.example.engineerapplication.data.response.AddResponse

class AddViewModel : ViewModel() {
    private var _toast = MutableLiveData<String>()
    val toast: LiveData<String>
        get() = _toast

    private var _materlist = MutableLiveData<List<ListMaterialModel>>()
    val materlist: LiveData<List<ListMaterialModel>>
        get() = _materlist

    private var _success = MutableLiveData<Boolean>()
    val success: LiveData<Boolean>
        get() = _success

    fun add(req: AddMaterialRequest): AddResponse {
        val response = AddResponse()
        when {
            req.materialname.isNullOrBlank() -> _toast.value = "กรุณาตรวจสอบข้อมูล"
            req.price.isNullOrBlank() -> _toast.value = "กรุณาตรวจสอบข้อมูล"

            else -> {
                val result = DataSource.addmaterial(req)
                if (result.success == true) {
                    _success.value=result.success
//                    _toast.value = result.messageval
                } else {
                    _success.value=result.success
                    _toast.value = result.messageval
                }

            }

        }
        return response
    }

    fun mater() {
        val result = DataSource.materlist()
        _materlist.value = result
    }

}