package com.example.easyfix.data.database

import com.example.easyfix.data.database.Type_technician.autoIncrement
import org.jetbrains.exposed.sql.Table

object Material:Table("material") {
    val material_id= integer("material_id").autoIncrement()
    val material_name= varchar("material_name",20)
    val price= integer("price")
    override val primaryKey: PrimaryKey?
        get() = PrimaryKey(Material.material_id, name = "material_id_PK")

}