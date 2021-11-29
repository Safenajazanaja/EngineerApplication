package com.example.engineerapplication.presentation.deal.chekpay

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.easyfix.data.datasource.DataSource
import com.example.engineerapplication.data.models.HistoryModel2
import com.example.engineerapplication.data.models.OrderModeldetail
import com.example.engineerapplication.data.request.HistoryRequest
import com.example.engineerapplication.presentation.history.HistoryViewModel
import com.google.gson.Gson
import java.text.SimpleDateFormat

class ChekpayViewModel : ViewModel() {

    private var _toast = MutableLiveData<String>()
    val toast: LiveData<String>
        get() = _toast

    private var _history2 = MutableLiveData<List<HistoryModel2>>()
    val history2: LiveData<List<HistoryModel2>>
        get() = _history2

    fun chekpat() {

        // list one
        val result = DataSource.chekpay()
//                _history.value = result

        if (result!=null){
            val sdf = SimpleDateFormat("dd/MM/yyyy")
            val result2 = result
//                    .sortedBy { sdf.format(it.date) } //ORDER BY
//                    .distinctBy { sdf.format(it.date) } // group by
                .map { db ->
                    HistoryModel2(
//                        order = db.order,
//                        adode = db.adode,
//                        repair_List = db.repair_List,
                        date = sdf.format(db.date),
                        datelong = db.date,
                        sumOrderByDate = result.filter { sdf.format(it.date) == sdf.format(db.date) }
                            .count(),
                        orders = result.filter { sdf.format(it.date) == sdf.format(db.date) }
                            .map {
                                OrderModeldetail(
                                    orderid = it.order,
                                    adode = it.adode,
                                    repair_List = it.repair_List,
                                    date = sdf.format(it.date),
                                    price = it.price,
                                    status = it.status,
                                    type = it.type
                                )
                            }
                    )
                }.sortedBy { it.datelong }.distinctBy { it.date }
            Log.d(TAG, "repair: ${Gson().toJson(result)}")
            _history2.value = result2
        }
    }
    companion object {
        private const val TAG = "ChekpayViewModel"
    }
}
