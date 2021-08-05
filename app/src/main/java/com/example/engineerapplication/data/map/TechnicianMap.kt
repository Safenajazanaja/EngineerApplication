package com.example.engineerapplication.data.map

import com.example.engineerapplication.data.database.Technician
import com.example.engineerapplication.data.models.Technician1Model
import com.example.engineerapplication.data.models.Technician2Model
import org.jetbrains.exposed.sql.ResultRow

object TechnicianMap {
    fun toTechnician1(row: ResultRow) = Technician1Model(
        id = row[Technician.technician_id],
        name = row[Technician.fullname]

    )

    fun toTechnician2(row: ResultRow) = Technician2Model(
        id = row[Technician.technician_id],

    )
}