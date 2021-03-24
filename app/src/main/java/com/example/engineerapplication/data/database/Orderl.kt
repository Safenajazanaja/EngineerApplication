package com.example.easyfix.data.database

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.jodatime.date


object Orderl: Table("orderl"){


    val order_id = integer("order_id").autoIncrement()
    val user_id= integer("user_id")
    val abode= varchar("abode",100)
    val repair_list= varchar("repair_list",50)
    val pay_type= integer("pay_type")
    val date=date("date")
    val dateLong=long("date_long")
    val price=integer("price")
    val latitude =double("latitude")
    val longitude=double("longitude")
    val type_job=integer("type_job")
    val date_end=long("date_end")
    val id_technician=integer("id_technician")
    override val primaryKey: PrimaryKey?
        get() = PrimaryKey(order_id, name = "order_id_PK")

}