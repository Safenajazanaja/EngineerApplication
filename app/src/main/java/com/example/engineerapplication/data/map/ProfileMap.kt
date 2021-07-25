package com.example.engineerapplication.data.map

import com.example.engineerapplication.data.database.Technician
import com.example.engineerapplication.data.models.ProfileModel
import org.jetbrains.exposed.sql.ResultRow

object ProfileMap {
    fun  toProfile(row: ResultRow)=ProfileModel(
            username = row[Technician.username],
            name = row[Technician.fullname],
            telephone = row[Technician.phone],
            img = row[Technician.image]
    )
}