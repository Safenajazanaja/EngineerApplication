package com.example.engineerapplication.data.database


import org.jetbrains.exposed.sql.Table

object Technician : Table("technician"){
    val technician_id = integer("technician_id").autoIncrement()
    val username = varchar("username", 20)
    val password = varchar("password", 20)
    val fullname = varchar("fullname", 30)
    val phone = varchar("phone", 10)
    val id_type_technician=integer("id_type_technician")
    val role=integer("role")
    val image = varchar("image",1000)

    override val primaryKey: PrimaryKey?
        get() = PrimaryKey(technician_id, name = "echnician_id_PK")

}