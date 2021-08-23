package com.example.engineerapplication.presentation.admin.workjob.manage

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.easyfix.data.datasource.DataSource
import com.example.engineerapplication.data.database.Technician
import com.example.engineerapplication.data.models.ManageModel
import com.example.engineerapplication.data.models.Technician1Model
import com.example.engineerapplication.data.models.Technician3Model
import com.example.engineerapplication.data.request.ChekTec2
import com.example.engineerapplication.data.request.ChekTecaddRequest
import com.example.engineerapplication.data.request.ConfimtecRequest
import com.example.engineerapplication.data.request.LoginRequest
import com.example.engineerapplication.data.response.LoginResponse
import com.example.engineerapplication.presentation.history.HistoryViewModel
import com.google.gson.Gson
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.andWhere
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class ManageViewModel : ViewModel() {


    private var _workjob = MutableLiveData<ManageModel>()
    val workjob: LiveData<ManageModel>
        get() = _workjob

    private var _chektec = MutableLiveData<List<Technician1Model>>()
    val chektec: LiveData<List<Technician1Model>>
        get() = _chektec

    fun mana(idjob: Int) {
        _workjob.value = DataSource.manage(idjob)
    }

    fun confim(req: ConfimtecRequest){
        DataSource.confimtec(req)

    }


    fun chek(req: ChekTec2) {
        val result = DataSource.chektec1() // technicial

        val result4 = mutableListOf<Technician1Model>()
        result.forEach {db ->
            val add = ChekTecaddRequest(id_tec = db.id!!, id_time = req.id_time, date = req.date)
            val result3 = DataSource.chektec2(add!!) // order
                if (result3.success==true){
                    result4.add(Technician1Model(id = db.id, name = db.name))
                }
//            Log.d(TAG, "add24: ${result4}")
        }

        _chektec.value  = result4

//        result.map { db ->
//            val add = ChekTecaddRequest(id_tec = db.id!!, id_time = req.id_time, date = req.date)
//            val result3 = DataSource.chektec2(add!!)
//            Log.d(TAG, "add23: ${result3}")
//            val result4 = result.filter { result3.success ==true }.map { Technician1Model(id = it.id, name = it.name) }
//            _chektec.value=result4
//            Log.d(TAG, "add24: ${result4}")
//        }
    }



    companion object {
        private const val TAG = "saaa"
    }


}
