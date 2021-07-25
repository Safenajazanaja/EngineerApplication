package com.example.engineerapplication.data.map

import com.example.engineerapplication.data.database.Orderl
import com.example.engineerapplication.data.models.ImagModel

import org.jetbrains.exposed.sql.ResultRow

object ImageMap {

    fun toImage(row: ResultRow)= ImagModel(
        img = row[Orderl.image]
    )
}