package com.example.engineerapplication.data.map


import com.example.engineerapplication.data.database.Material
import com.example.engineerapplication.data.database.Orderl_detail
import com.example.engineerapplication.data.models.ChekMaterialModel
import com.example.engineerapplication.data.models.SetViewMatialModel
import org.jetbrains.exposed.sql.ResultRow

object MaterialMap {
    fun toChekMaterial(row: ResultRow) = ChekMaterialModel(
        id = row[Material.material_id],
        name = row[Material.material_name],
        qty = row[Orderl_detail.qty],
        price = row[Material.price_material]
    )
    fun  toSetViewMatial(row: ResultRow)=SetViewMatialModel(
        id = row[Material.material_id],
        name = row[Material.material_name],
        price = row[Material.price_material],


    )
}