package com.example.engineerapplication.data.map

import com.example.engineerapplication.data.database.*
import com.example.engineerapplication.data.models.DetailModel
import org.jetbrains.exposed.sql.ResultRow

object DetailMap {
    fun toDetailMap(row: ResultRow) = DetailModel(
        order_id = row[Orderl.order_id],
        abode = row[Orderl.abode],
        repair_list = row[Orderl.repair_list],
        date = row[Orderl.dateLong],
        latitudeval = row[Orderl.latitude],
        longitude = row[Orderl.longitude],
        typejob = row[Type_job.namejob],
        timezone = row[Time.time],
        idtime = row[Orderl.idtime],
        status = row[Status.status_name],
        province_name = row[Province.province_name],
        amphur_name = row[Amphur.amphur_name],
        district_name = row[District.district_name],
    )
}