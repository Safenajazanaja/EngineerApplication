package com.example.engineerapplication.data.map

import com.example.engineerapplication.data.database.Orderl
import com.example.engineerapplication.data.database.Status
import com.example.engineerapplication.data.database.Type_job
import com.example.engineerapplication.data.models.HistoryModel

import org.jetbrains.exposed.sql.ResultRow

object HistoryMap {
    fun toHistory(row: ResultRow)= HistoryModel(
        order = row[Orderl.order_id],
        adode = row[Orderl.abode],
        repair_List = row[Orderl.repair_list],
        date = row[Orderl.dateLong],
        price = row[Orderl.price],
        status = row[Status.status_name],
        type = row[Type_job.namejob]

    )
//    fun toOrder(row: ResultRow)=OrderModel(
//        order = row[Orderl.order_id],
//        adode = row[Orderl.abode],
//        repair_List = row[Orderl.repair_list],
//        date = row[Orderl.dateLong],
//        price = row[Orderl.price]
//    )
//    fun toOrderdetail(row: ResultRow)=HistoryDetailModel(
//        order = row[Orderl.order_id],
//        adode = row[Orderl.abode],
//        repair_List = row[Orderl.repair_list],
//        date = row[Orderl.dateLong],
//        price = row[Orderl.price]
//    )
}