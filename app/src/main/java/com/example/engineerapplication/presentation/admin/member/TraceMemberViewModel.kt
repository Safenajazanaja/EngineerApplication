package com.example.engineerapplication.presentation.admin.member

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.easyfix.data.datasource.DataSource
import com.example.engineerapplication.data.models.HistoryModel2
import com.example.engineerapplication.data.models.OrderModeldetail
import com.google.gson.Gson
import java.text.SimpleDateFormat

class TraceMemberViewModel: ViewModel() {

    private var _trace = MutableLiveData<List<HistoryModel2>>()
    val trace: LiveData<List<HistoryModel2>>
        get() = _trace

    fun tracejob() {
        val result = DataSource.tracememberjob()
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        val date = result
//            .sortedBy { sdf.format(it.date) }
//            .distinctBy { sdf.format(it.date) }
            .map { db ->
                HistoryModel2(
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
            }.sortedBy { it.date }
            .distinctBy { it.date }
        Log.d(TAG,"trace: ${Gson().toJson(date)}")

        _trace.value = date
    }

    companion object{
        private  const val TAG="TraceMemberViewModel"
    }
}