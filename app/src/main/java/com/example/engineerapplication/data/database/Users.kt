package com.example.engineerapplication.data.database

import org.jetbrains.exposed.sql.Table

object Users : Table("user") {
    val user_id = integer("user_id").autoIncrement()
    val username = varchar("username", 20)
    val password = varchar("password", 20)
    val fullname = varchar("fullname", 30)
    val phone = varchar("phone", 10)
    val image =varchar("image",1000)
    val abode=varchar("abode",50)

    //    val geo_id=integer("geo_id").references(Geography.geo_id)
    val province_id=integer("province_id").references(Province.province_id)
    val amphur_id= integer("amphur_id").references(Amphur.amphur_id)
    val district_id= integer("district_id").references(District.district_id)

    override val primaryKey: PrimaryKey?
        get() = PrimaryKey(user_id, name = "user_id_PK")


}