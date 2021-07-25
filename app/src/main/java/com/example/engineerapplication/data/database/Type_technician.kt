package com.example.engineerapplication.data.database

import org.jetbrains.exposed.sql.Table

object Type_technician: Table("type_technician") {
    val type_engineer= integer("type_engineer").autoIncrement()
    val name_engineer= varchar("name_engineer",20)
    override val primaryKey: PrimaryKey?
        get() = PrimaryKey(type_engineer, name = "type_engineer_PK")
}