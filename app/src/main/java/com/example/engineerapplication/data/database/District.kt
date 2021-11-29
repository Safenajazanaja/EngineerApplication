package com.example.engineerapplication.data.database

import org.jetbrains.exposed.sql.Table

object District:Table("district") {
    val district_id =integer("district_id").autoIncrement()
    val district_name=varchar("district_name",150)
    val amphur_id=integer("amphur_id").references(Amphur.amphur_id)
//    val province_id=integer("province_id").references(Province.province_id)
//    val geo_id=integer("geo_id").references(Geography.geo_id)

    override val primaryKey: PrimaryKey?
        get() = PrimaryKey(district_id, name = "district_id_PK")
}