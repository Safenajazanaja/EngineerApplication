package com.example.easyfix.data.database

import android.icu.number.Precision.integer
import org.jetbrains.exposed.sql.Table


object Orderl_detail:Table("order_detail") {
    val orderl_detail_id= integer("orderl_detail_id").autoIncrement()
    val orderl_id= integer("orderl_id")
    val material_id= integer("material_id")
    val qty= integer("qty")
    override val primaryKey: PrimaryKey?
        get() = PrimaryKey(Orderl_detail.orderl_detail_id, name = "orderl_detail_id_PK")
}