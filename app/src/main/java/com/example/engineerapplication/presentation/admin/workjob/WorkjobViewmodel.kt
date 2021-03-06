package com.example.engineerapplication.presentation.admin.workjob

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.easyfix.data.datasource.DataSource
import com.example.engineerapplication.data.models.OrderaddModel
import com.example.engineerapplication.data.models.WorkaddTec2Model
import java.text.SimpleDateFormat

class WorkjobViewmodel : ViewModel() {

    private var _toast = MutableLiveData<String>()
    val toast: LiveData<String>
        get() = _toast

    private var _work = MutableLiveData<List<WorkaddTec2Model>>()
    val work: LiveData<List<WorkaddTec2Model>>
        get() = _work

    fun workjob() {
        val result = DataSource.workjob()
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        val date = result
            .map { db ->
                WorkaddTec2Model(
                    date = sdf.format(db.date),
                    datelong = db.date,
                    sumOrderByDate = result.filter { sdf.format(it.date) == sdf.format(db.date) }
                        .count(),
                    orders = result.filter { sdf.format(it.date) == sdf.format(db.date) }
                        .map {
                            OrderaddModel(
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
            }.sortedBy { it.datelong }
            .distinctBy { it.date }

        _work.value=date

    }


}