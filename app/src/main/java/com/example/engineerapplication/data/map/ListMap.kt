package com.example.engineerapplication.data.map

import com.example.engineerapplication.data.database.Material
import com.example.engineerapplication.data.database.Orderl_detail
import com.example.engineerapplication.data.models.ListModel
import org.jetbrains.exposed.sql.ResultRow

object ListMap {
    fun tolist(row: ResultRow)= ListModel(
        name = row[Material.material_name],
        price = row[Orderl_detail.qty],
        Unitprice = row[Material.price_material],
        qty = row[Orderl_detail.qty]

    )
}