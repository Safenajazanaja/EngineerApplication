package com.example.engineerapplication.data.map

import com.example.engineerapplication.data.database.Orderl
import com.example.engineerapplication.data.database.Status
import com.example.engineerapplication.data.models.OrderdetailModel
import org.jetbrains.exposed.sql.ResultRow

object TableJobMap {
    fun toTableJob(row: ResultRow)=OrderdetailModel(
        orderid = row[Orderl.order_id],
        adode = row[Orderl.abode],
        repair_List = row[Orderl.repair_list],
        date = row[Orderl.dateLong],
        price = row[Orderl.price],
        status = row[Status.status_name]
    )
}