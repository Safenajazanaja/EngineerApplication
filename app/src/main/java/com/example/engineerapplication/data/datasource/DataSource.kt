package com.example.easyfix.data.datasource


import android.util.Log
import com.example.engineerapplication.data.database.*
import com.example.engineerapplication.data.map.*
import com.example.engineerapplication.data.models.*
import com.example.engineerapplication.data.request.*
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


    fun tablejob(userid: Int): List<OrderdetailModel> {
        return transaction {
            addLogger(StdOutSqlLogger)
            (Orderl innerJoin Status)
                .slice(
                    Orderl.abode,
                    Orderl.order_id,
                    Orderl.repair_list,
                    Orderl.dateLong,
                    Orderl.price, //add
                    Status.status_name
                )
                .select { Orderl.id_technician eq userid }
                .map { TableJobMap.toTableJob(it) }
        }
    }

    fun listitem(jobid: Int): List<ListModel> {
        return transaction {
            addLogger(StdOutSqlLogger)
            (Orderl_detail innerJoin Material)
                .slice(
                    Material.material_name,
                    Material.price_material,
                    Orderl_detail.qty
                )
                .select { Orderl_detail.orderl_id eq jobid }
                .map { ListMap.tolist(it) }
        }

    }

    fun chekImage(idjob: Int): ImagModel {
        return transaction {
            addLogger(StdOutSqlLogger)
            Orderl.slice(Orderl.image)
                .select { Orderl.order_id eq idjob }
                .map { ImageMap.toImage(it) }
                .single()
        }

    }

    fun ckek(req: CkkRequest): ChekMaterialModel {
        val response = ChekMaterialModel()
        val result = transaction {
            addLogger(StdOutSqlLogger)
            (Orderl_detail innerJoin Material)
                .slice(
                    Material.material_id,
                    Material.material_name,
                    Material.price_material,
                    Orderl_detail.qty
                )
                .select { Material.material_id eq req.idmaterial!!.toInt() }
                .count()
                .toInt()
        }
        if (result == 0) {
            response.success = false
            response.id = 0
            response.name = "0"
            response.qty = 0
            response.price = 0
        } else {
            val result2 = transaction {
                addLogger(StdOutSqlLogger)
                (Orderl_detail innerJoin Material)
                    .slice(
                        Material.material_id,
                        Material.material_name,
                        Material.price_material,
                        Orderl_detail.qty
                    )
                    .select { Material.material_id eq req.idmaterial!!.toInt() }
                    .map { MaterialMap.toChekMaterial(it) }
                    .single()

            }
            response.success = true
            response.id = result2.id
            response.name = result2.name
            response.qty = result2.qty
            response.price = result2.price

        }
        return response

    }

    fun setviewmaterial(idjob: Int): List<SetViewMatialModel> {
        return transaction {
            addLogger(StdOutSqlLogger)
            Material
                .slice(
                    Material.material_name,
                    Material.price_material,
                    Material.material_id,
                )
                .selectAll()
                .map { MaterialMap.toSetViewMatial(it) }
        }
    }

    fun add(req: AddRequest) {
        return transaction {
            addLogger(StdOutSqlLogger)
            Orderl_detail.update({ Orderl_detail.orderl_id eq req.orderid!!.toInt() }) {
                it[Orderl_detail.qty] = req.qty.toString().toInt()
            }
        }

    }

    fun addnew(req: AddRequest) {
        return transaction {
            addLogger(StdOutSqlLogger)
            Orderl_detail.insert {
                it[Orderl_detail.qty] = req.qty.toString().toInt()
                it[Orderl_detail.orderl_id] = req.orderid.toString().toInt()
                it[Orderl_detail.material_id] = req.materialid.toString().toInt()
            }
        }

    }

    fun history(req: HistoryRequest): List<HistoryModel> {


        return transaction {
            addLogger(StdOutSqlLogger)
            (Orderl innerJoin Status)
                .slice(
                    Orderl.abode,
                    Orderl.order_id,
                    Orderl.repair_list,
                    Orderl.dateLong,
                    Orderl.price, //add
                    Status.status_name
                )
                .select { Orderl.id_technician eq req.id }
                .andWhere { Orderl.dateLong.between(req.star, req.end) }
                .map { HistoryMap.toHistory(it) }


        }
    }


    fun tracememberjob(): List<HistoryModel> {
        return transaction {
            addLogger(StdOutSqlLogger)
            (Orderl innerJoin Status)
                .slice(
                    Orderl.abode,
                    Orderl.order_id,
                    Orderl.repair_list,
                    Orderl.dateLong,
                    Orderl.price, //add
                    Status.status_name
                )
                .select { Orderl.status neq 4 }
                .map { HistoryMap.toHistory(it) }
        }


    }

    fun workjob(): List<HistoryModel> {
        return transaction {
            addLogger(StdOutSqlLogger)
            (Orderl innerJoin Status)
                .slice(
                    Orderl.abode,
                    Orderl.order_id,
                    Orderl.repair_list,
                    Orderl.dateLong,
                    Orderl.price, //add
                    Status.status_name
                )
                .select { Orderl.status eq 1 }
                .map { HistoryMap.toHistory(it) }
        }


    }

    fun workaddtecjob(): List<WorkaddTecModel> {
        return transaction {
            addLogger(StdOutSqlLogger)
            (Orderl innerJoin Status)
                .slice(
                    Orderl.order_id,
                    Orderl.abode,
                    Orderl.repair_list,
                    Orderl.dateLong,
                    Orderl.price, //add
                    Status.status_name
                )
                .select { Orderl.status eq 1 }
                .map { WorkAddTecjob.toWorkaddTec(it) }
        }


    }

    fun manage(idjob: Int): ManageModel {
        return transaction {
            addLogger(StdOutSqlLogger)
            (Orderl innerJoin Time innerJoin Type_job)
                .slice(
                    Orderl.order_id,
                    Orderl.abode,
                    Orderl.repair_list,
                    Orderl.dateLong,
                    Orderl.latitude,
                    Orderl.longitude,
                    Type_job.namejob,
                    Time.time,
                    Orderl.idtime
                )
                .select { Orderl.order_id eq idjob }
                .map { ManageMap.toManageMap(it) }
                .single()

        }
    }

//    fun chektec2(req: ChekTec2): List<Technician1Model> {
//        return transaction {
//            addLogger(StdOutSqlLogger)
//            (Orderl innerJoin Technician)
//                .slice(
//                    Technician.technician_id,
//                    Technician.fullname
//                )
//                .select { Orderl.dateLong eq req.date }
//                .andWhere { Orderl.idtime neq req.time }
//                .map { TechnicianMap.toTechnician1(it) }
//        }
//    }

    fun chektec1(): List<Technician1Model> {
        return transaction {
            addLogger(StdOutSqlLogger)
            Technician
                .slice(
                    Technician.technician_id,
                    Technician.fullname
                )
                .selectAll()
                .map { TechnicianMap.toTechnician1(it) }
        }
    }

    fun chektec2(req: ChekTecaddRequest): Technician2Model {
        val response =Technician2Model()
        val result= transaction {
            addLogger(StdOutSqlLogger)
            Orderl
                .slice(
                    Orderl.id_technician
                )
                .select { Orderl.id_technician eq req.id_tec }
                .andWhere { Orderl.dateLong eq req.date }
                .andWhere { Orderl.idtime eq req.id_time }
                .count()
                .toInt()
        }

        if (result==1){
            response.id=req.id_tec
            response.success=true

        }else{
            response.success=false

        }
        return response


    }




}

