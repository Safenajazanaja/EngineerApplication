package com.example.engineerapplication.data.database

import org.jetbrains.exposed.sql.Table

object Province:Table("province") {
    val province_id = integer("province_id").autoIncrement()
//    val province_code=varchar("province_code",2)
    val province_name= varchar("province_name",150)
//    val geo_id=integer("geo_id").references(Geography.geo_id)

    override val primaryKey: PrimaryKey?
        get() = PrimaryKey(province_id, name = "province_id_PK")


}