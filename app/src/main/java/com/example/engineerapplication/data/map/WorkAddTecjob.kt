package com.example.engineerapplication.data.map

import com.example.engineerapplication.data.database.Orderl
import com.example.engineerapplication.data.database.Status
import com.example.engineerapplication.data.models.WorkaddTecModel
import org.jetbrains.exposed.sql.ResultRow

object WorkAddTecjob {
    fun toWorkaddTec(row: ResultRow)= WorkaddTecModel(
        order = row[Orderl.order_id],
        adode = row[Orderl.abode],
        repair_List = row[Orderl.repair_list],
        date = row[Orderl.dateLong],
        price = row[Orderl.price],
        status = row[Status.status_name]
    )
}