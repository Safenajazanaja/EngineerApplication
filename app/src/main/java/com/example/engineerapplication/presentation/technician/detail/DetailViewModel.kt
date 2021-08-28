package com.example.engineerapplication.presentation.technician.detail


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.easyfix.data.datasource.DataSource
import com.example.engineerapplication.data.models.ImagModel
import com.example.engineerapplication.data.models.ListModel
import com.example.engineerapplication.data.models.SumpriceModel
import com.example.engineerapplication.data.request.PriceTecRequest
import com.google.gson.Gson


class DetailViewModel : ViewModel() {

    private var _toast = MutableLiveData<String>()
    val toast: LiveData<String>
        get() = _toast

    private var _list = MutableLiveData<List<ListModel>>()
    val list: LiveData<List<ListModel>>
        get() = _list
    private val _imgpayModel = MutableLiveData<ImagModel>()
    val imgpayModel: LiveData<ImagModel>
        get() = _imgpayModel

    private var _statusjob = MutableLiveData<Int>()
    val statusjob: LiveData<Int>
        get() = _statusjob

    private val _sumprice = MutableLiveData<List<SumpriceModel>>()
    val sumprice: LiveData<List<SumpriceModel>>
        get() = _sumprice


    private var _pricetec = MutableLiveData<Int>()
    val pricetec: LiveData<Int>
        get() = _pricetec

    fun pricetec(req: PriceTecRequest) {
        DataSource.addpricetec(req)
    }

    fun chekpricetec(idjob: Int) {
        val result = DataSource.chekpricetec(idjob)
        if (result.price == null) {
            _pricetec.value = 0
        } else {
            _pricetec.value = result.price
        }

    }

    fun listdetail(request: Int) {
        val result = DataSource.listitem(request)

        val sss = result.map { db ->
            SumpriceModel(
                sum = db.qty!! * db.Unitprice!!
            )

        }

        Log.d(TAG, "ssss: ${Gson().toJson(sss)}")
        _sumprice.value = sss
        _list.value = result
    }

    fun chekImg(jobid: Int) {
        _imgpayModel.value = DataSource.chekImage(jobid)

    }

    fun cancel(jobid: Int) {
        DataSource.canceljob(jobid)

    }

    fun confim(jobid: Int) {
        DataSource.confimjob(jobid)

    }
    fun finishjob(jobid: Int) {
        DataSource.finishjob(jobid)

    }
    fun chekstatusjob(idjob: Int) {
        val request = DataSource.chekstatusjob(idjob)
        _statusjob.value = request.status

    }

    companion object {
        private const val TAG = "www"
    }
}