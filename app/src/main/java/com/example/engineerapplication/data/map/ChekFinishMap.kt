package com.example.engineerapplication.data.map

import com.example.engineerapplication.data.database.Orderl
import com.example.engineerapplication.data.models.ChekfinishModel
import com.example.engineerapplication.data.models.DetailModel
import org.jetbrains.exposed.sql.ResultRow

object ChekFinishMap {
    fun toChekFinishMap(row: ResultRow) = ChekfinishModel(
        sts = row[Orderl.status]
    )
}