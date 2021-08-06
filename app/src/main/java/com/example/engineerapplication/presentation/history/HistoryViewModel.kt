package com.example.engineerapplication.presentation.history

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.easyfix.data.datasource.DataSource
import com.example.engineerapplication.data.models.HistoryModel2
import com.example.engineerapplication.data.models.OrderModeldetail
import com.example.engineerapplication.data.request.HistoryRequest
import com.google.gson.Gson
import java.text.SimpleDateFormat


class HistoryViewModel : ViewModel() {
    private var _toast = MutableLiveData<String>()
    val toast: LiveData<String>
        get() = _toast

//    private var _repair = MutableLiveData<Boolean>()
//    val repair: LiveData<Boolean>
//        get() = _repair

//    private var _history = MutableLiveData<List<HistoryModel>>()
//    val history: LiveData<List<HistoryModel>>
//        get() = _history

    private var _history2 = MutableLiveData<List<HistoryModel2>>()
    val history2: LiveData<List<HistoryModel2>>
        get() = _history2

//    private var _history3 = MutableLiveData<List<OrderModeldetail>>()
//    val history3: LiveData<List<OrderModeldetail>>
//        get() = _history3

    fun repair(request: HistoryRequest) {
        when {
            request.star == null -> _toast.value = "กรุณาเลือกวันที่เริ่มต้น"
            request.end == null -> _toast.value = "กรุณาเลือกวันที่สิ้นสุด"


            else -> {
                // list one
                val result = DataSource.history(request)
//                _history.value = result

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
                                        status = it.status
                                    )
                                }
                        )
                    }.sortedBy { it.date }.distinctBy { it.date }
                Log.d(TAG, "repair: ${Gson().toJson(result)}")
                _history2.value = result2

            }

        }

    }

    companion object {
        private const val TAG = "HistoryViewModel"
    }

}
