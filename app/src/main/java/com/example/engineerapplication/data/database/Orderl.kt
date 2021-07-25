package com.example.engineerapplication.data.database

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.jodatime.date


object Orderl: Table("orderl"){

    val order_id = integer("order_id").autoIncrement()
    val user_id= integer("user_id").references(Users.user_id)
    val abode= varchar("abode",100)
    val repair_list= varchar("repair_list",50)
    val pay_type= integer("pay_type").references(Pay.pay_id)
    val date=date("date")
    val dateLong=long("date_long")
    val price=integer("price")
    val id_technician=integer("id_technician").references(Technician.technician_id)
    val latitude =double("latitude")
    val longitude=double("longitude")
    val type_job=integer("type_job").references(Type_job.type_job_id)
    val date_end=long("date_end")
    val status=integer("status_id").references(Status.status_id)
    val image = varchar("image",1000)

    override val primaryKey: PrimaryKey?
        get() = PrimaryKey(order_id, name = "order_id_PK")

}