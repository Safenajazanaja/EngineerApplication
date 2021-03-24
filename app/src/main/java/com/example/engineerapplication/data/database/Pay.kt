package com.example.easyfix.data.database

import com.example.easyfix.data.database.Orderl_detail.autoIncrement
import org.jetbrains.exposed.sql.Table

object Pay:Table("pay") {
    val pay_id= integer("pay_id").autoIncrement()
    val pay_type= integer("pay_type")
    override val primaryKey: PrimaryKey?
        get() = PrimaryKey(Pay.pay_id, name = "pay_id_PK")
}