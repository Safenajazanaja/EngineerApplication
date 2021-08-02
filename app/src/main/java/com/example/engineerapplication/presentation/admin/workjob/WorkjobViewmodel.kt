package com.example.engineerapplication.presentation.admin.workjob

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.easyfix.data.datasource.DataSource
import com.example.engineerapplication.data.models.HistoryModel2
import com.example.engineerapplication.data.models.OrderModeldetail
import java.text.SimpleDateFormat

class WorkjobViewmodel : ViewModel() {

    private var _toast = MutableLiveData<String>()
    val toast: LiveData<String>
        get() = _toast

    private var _work = MutableLiveData<List<HistoryModel2>>()
    val work: LiveData<List<HistoryModel2>>
        get() = _work

    fun workjob() {
        val result = DataSource.workjob()
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        val date = result
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

        _work.value=date

    }


}