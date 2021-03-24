package com.example.easyfix.data.datasource


import com.example.easyfix.data.database.*
import com.example.engineerapplication.data.map.RoleMap
import com.example.engineerapplication.data.models.RoleModel
import com.example.engineerapplication.data.request.LoginRequest
import com.example.engineerapplication.data.response.LoginResponse
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

object DataSource {

    fun login(request: LoginRequest): LoginResponse {
        val response = LoginResponse()
        val result = transaction {
            addLogger(StdOutSqlLogger)

            Technician.select {
                Technician.username eq request.username
            }
                .andWhere { Technician.password eq request.password }
                .count()
                .toInt()

        }

        if (result == 1) {
            val userId = transaction {
                Technician.slice(Technician.technician_id)
                    .select { Technician.username eq request.username }
                    .andWhere { Technician.password eq request.password }
                    .map { it[Technician.technician_id] }
                    .single()
            }
            response.userId = userId
            response.success = true
//            Log.d(ContentValues.TAG, "onActivityCreated: "+userId)
        } else {
            response.success = false
        }
        return response
    }

     fun checkrole(userId: Int): RoleModel {
        return transaction {
            addLogger(StdOutSqlLogger)
            Technician
                .slice(
                    Technician.role,
                )
                .select {Technician.technician_id eq userId }
                .map { RoleMap.toUser(it) }
                .single()
        }
    }

}