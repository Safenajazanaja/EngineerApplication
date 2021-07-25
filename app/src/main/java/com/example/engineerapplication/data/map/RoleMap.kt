package com.example.engineerapplication.data.map

import com.example.engineerapplication.data.database.Technician
import com.example.engineerapplication.data.models.RoleModel
import org.jetbrains.exposed.sql.ResultRow

object RoleMap {
    fun toUser(row: ResultRow) = RoleModel(
        role = row[Technician.role]
    )
}