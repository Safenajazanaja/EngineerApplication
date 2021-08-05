package com.example.engineerapplication.data.models

data class ManageModel (
    val order_id:Int?=null,
    val abode:String,
    val repair_list:String,
    val date: Long?=null,
    val latitudeval :Double?=null,
    val longitude:Double?=null,
    val typejob:String?=null,
    val timezone:String?=null,
    val idtime:Int?=null
)