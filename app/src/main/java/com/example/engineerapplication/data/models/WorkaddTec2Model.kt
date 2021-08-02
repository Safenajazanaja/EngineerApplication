package com.example.engineerapplication.data.models

data class WorkaddTec2Model(
    val datelong:Long?=null,
    val date: String? = null,
    val sumOrderByDate: Int,
    val orders: List<OrderaddModel>
)
