package com.example.easyfix.data.datasource


import android.util.Log
import com.example.engineerapplication.data.database.Technician
import com.example.engineerapplication.data.map.ProfileMap
import com.example.engineerapplication.data.map.RoleMap
import com.example.engineerapplication.data.models.ProfileModel
import com.example.engineerapplication.data.models.RoleModel
import com.example.engineerapplication.data.request.ImagsRequest
import com.example.engineerapplication.data.request.LoginRequest
import com.example.engineerapplication.data.response.LoginResponse
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

object DataSource {
    private const val TAG = "####"

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
                .select { Technician.technician_id eq userId }
                .map { RoleMap.toUser(it) }
                .single()
        }
    }

    fun profile(userId: Int): ProfileModel {
        return transaction {
            addLogger(StdOutSqlLogger)
            Technician
                .slice(
                    Technician.username,
                    Technician.fullname,
                    Technician.phone,
                    Technician.image
                )
                .select { Technician.technician_id eq userId }
                .map { ProfileMap.toProfile(it) }
                .single()
        }
    }


    fun upimgprofile(req: ImagsRequest) {
        return transaction {
            addLogger(StdOutSqlLogger)
//            val result = Users.update({ Users.image eq req.imags }) {
//                it[user_id] = req.id
//            }

            val result = Technician.update({ Technician.technician_id eq req.id }) {
                it[Technician.image] = req.imags
            }

//             Users.select {  }
//             Users.deleteWhere { Users.user_id eq req.id }

            Log.d(TAG, "upimg: $req")
            Log.d(TAG, "upimg: $result")

        }

    }

}

