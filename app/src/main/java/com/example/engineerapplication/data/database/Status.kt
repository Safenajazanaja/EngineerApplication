package com.example.engineerapplication.data.database



import org.jetbrains.exposed.sql.Table

object Status:Table("status") {
    val status_id = integer("status_id").autoIncrement()
    val status_name= varchar("status_name",20)

    override val primaryKey: PrimaryKey?
        get() = PrimaryKey(status_id, name = "status_id_PK")
}