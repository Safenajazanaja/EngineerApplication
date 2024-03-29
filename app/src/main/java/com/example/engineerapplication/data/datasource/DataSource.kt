package com.example.easyfix.data.datasource


import android.util.Log
import com.example.engineerapplication.data.database.*
import com.example.engineerapplication.data.map.*
import com.example.engineerapplication.data.models.*
import com.example.engineerapplication.data.request.*
import com.example.engineerapplication.data.response.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime

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
                .andWhere { Orderl.status neq 4 }
                .andWhere { Orderl.status neq 5 }
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
                .andWhere { Orderl_detail.orderl_id eq req.idjob!!.toInt() }
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
                    .andWhere { Orderl_detail.orderl_id eq req.idjob!!.toInt() }
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
            Orderl_detail.update({ (Orderl_detail.orderl_id eq req.orderid!!.toInt()) and (Orderl_detail.material_id eq req.materialid!!.toInt()) }) {
                it[Orderl_detail.qty] = req.qty.toString().toInt()
            }
        }

    }

    fun updatepay(req: UpdatePayRequest) {
        return transaction {
            addLogger(StdOutSqlLogger)
            Orderl.update({ (Orderl.order_id eq req.orderid!!.toInt()) }) {
                it[Orderl.pay_type] = req.payid.toString().toInt()
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
            (Orderl innerJoin Status innerJoin Type_job)
                .slice(
                    Orderl.abode,
                    Orderl.order_id,
                    Orderl.repair_list,
                    Orderl.dateLong,
                    Orderl.price, //add
                    Status.status_name,
                    Type_job.namejob
                )
                .select { Orderl.id_technician eq req.id }
                .andWhere { Orderl.dateLong.between(req.star, req.end) }
                .map { HistoryMap.toHistory(it) }


        }
    }


    fun tracememberjob(): List<HistoryModel> {
        return transaction {
            addLogger(StdOutSqlLogger)
            (Orderl innerJoin Status innerJoin Type_job)
                .slice(
                    Orderl.abode,
                    Orderl.order_id,
                    Orderl.repair_list,
                    Orderl.dateLong,
                    Orderl.price, //add
                    Status.status_name,
                    Type_job.namejob
                )
                .select { Orderl.status neq 4 }
                .map { HistoryMap.toHistory(it) }
        }


    }

    fun workjob(): List<HistoryModel> {
        return transaction {
            addLogger(StdOutSqlLogger)
            (Orderl innerJoin Status innerJoin Type_job)
                .slice(
                    Orderl.abode,
                    Orderl.order_id,
                    Orderl.repair_list,
                    Orderl.dateLong,
                    Orderl.price, //add
                    Status.status_name,
                    Type_job.namejob
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

    fun detail(idjob: Int): DetailModel {
        return transaction {
            addLogger(StdOutSqlLogger)
            (Orderl innerJoin Time innerJoin Type_job innerJoin Status innerJoin Province innerJoin Amphur innerJoin District)
                .slice(
                    Orderl.order_id,
                    Orderl.abode,
                    Orderl.repair_list,
                    Orderl.dateLong,
                    Orderl.latitude,
                    Orderl.longitude,
                    Type_job.namejob,
                    Time.time,
                    Orderl.idtime,
                    Status.status_name,
                    Province.province_name,
                    Amphur.amphur_name,
                    District.district_name,
                )
                .select { Orderl.order_id eq idjob }
                .andWhere { Orderl.status eq Status.status_id }
                .andWhere { Orderl.province_id eq Province.province_id }
                .andWhere { Orderl.district_id eq District.district_id }
                .andWhere { Orderl.amphur_id eq Amphur.amphur_id }
                .map { DetailMap.toDetailMap(it) }
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

    fun materlist(): List<ListMaterialModel> {
        return transaction {
            addLogger(StdOutSqlLogger)
            Material
                .slice(
                    Material.material_name,
                    Material.price_material
                )
                .selectAll()
                .map { ListMap.tolistmat(it) }
        }
    }

    fun chektec2(req: ChekTecaddRequest): Technician2Model {
        val response = Technician2Model()
        val result = transaction {
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

        if (result >= 1) {

            response.success = false

        } else {
            response.id = req.id_tec
            response.success = true

        }
        return response


    }

    fun addpricetec(req: PriceTecRequest) {
        return transaction {
            addLogger(StdOutSqlLogger)
            Orderl.update({ Orderl.order_id eq req.orderid!!.toInt() }) {
                it[Orderl.price] = req.price!!.toInt()
            }
        }

    }

    fun chekpricetec(idjob: Int): ChekpricetecResponse {
        val response = ChekpricetecResponse()
        val result = transaction {

            Orderl.slice(Orderl.price)
                .select { Orderl.order_id eq idjob }
                .map { it[Orderl.price] }
                .single()
        }

        if (result == null) {
            response.price = 0
        } else {
            response.price = result
        }
        return response
    }

    fun chekperiod(idjob: Int): ChekperiodResponse {
        val response = ChekperiodResponse()
        val result = transaction {
            Orderl.slice(Orderl.period)
                .select { Orderl.order_id eq idjob }
                .map { it[Orderl.period] }
                .single()
        }

        if (result == 0L) {
            response.period = 0L
        } else {
            response.period = result
        }
        return response
    }




    fun canceljob(idjob: Int) {
        return transaction {
            Orderl.update({ Orderl.order_id eq idjob }) {
                it[Orderl.status] = 5
            }
        }


    }

    fun confimjob(idjob: Int) {
        return transaction {
            Orderl.update({ Orderl.order_id eq idjob }) {
                it[Orderl.status] = 3
            }
        }


    }

    fun dateconfim(req: DateSumRequest){
        return transaction {
            Orderl.update({Orderl.order_id eq req.id}){
                it[Orderl.period]=req.date.toLong()
            }
        }
    }

    fun finishjob(idjob: Int) {
        return transaction {
            Orderl.update({ Orderl.order_id eq idjob }) {
                it[Orderl.status] = 4
                it[Orderl.date_end] = DateTime.now().millis
            }
        }


    }

    fun chekstatusjob(idjob: Int): ChekStatusResponse {
        var req = ChekStatusResponse()
        val result = transaction {
            addLogger(StdOutSqlLogger)
            Orderl
                .slice(
                    Orderl.pay_type
                )
                .select { Orderl.order_id eq idjob }
                .map { it[Orderl.pay_type] }
                .single()
        }
        req.status = result
        return req

    }

    fun confimtec(req: ConfimtecRequest) {
        return transaction {
            Orderl.update({ Orderl.order_id eq req.id_job }) {
                it[Orderl.id_technician] = req.id_tec
                it[Orderl.status] = 2
            }
        }
    }

    fun addmaterial(req: AddMaterialRequest): AddResponse {
        val res = AddResponse()
        val result = transaction {
            addLogger(StdOutSqlLogger)
            Material.select {
                Material.material_name eq req.materialname
            }.count().toInt()
        }
        if (result == 1) {
            res.messageval = "ชื่อวัสดุก่อสร้างซ้ำ"
            res.success = false
        } else {
            transaction {
                Material.insert {
                    it[Material.material_name] = req.materialname
                    it[Material.price_material] = req.price.toInt()
                }
            }
            res.messageval = "บันทึกข้อมูลเสร็จสิ้น"
            res.success = true
        }
        return res

    }

    fun chekpay(): List<HistoryModel> {
        return transaction {
            addLogger(StdOutSqlLogger)
            (Orderl innerJoin Status innerJoin Type_job)
                .slice(
                    Orderl.abode,
                    Orderl.order_id,
                    Orderl.repair_list,
                    Orderl.dateLong,
                    Orderl.price, //add
                    Status.status_name,
                    Type_job.namejob
                )
                .select { Orderl.pay_type eq 1 }
                .map { HistoryMap.toHistory(it) }


        }
    }

    fun chekfinish(idjob: Int): ChekfinishModel {
        return transaction {
            addLogger(StdOutSqlLogger)
            (Orderl)
                .slice(
                    Orderl.status
                )
                .select { Orderl.order_id eq idjob }
                .map { ChekFinishMap.toChekFinishMap(it) }
                .single()
        }
    }


}

