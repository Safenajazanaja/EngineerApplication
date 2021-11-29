package com.example.engineerapplication.presentation.technician.detail


import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.engineerapplication.R
import com.example.engineerapplication.base.BaseActivity
import com.example.engineerapplication.base.Dru.loadImageCircle
import com.example.engineerapplication.data.request.DateSumRequest
import com.example.engineerapplication.data.request.PriceTecRequest
import com.example.engineerapplication.presentation.main.MainActivity
import com.example.engineerapplication.presentation.material.MaterialActivity
import com.example.loginmvvm.presentation.history.detail.ColumAdapter

import com.google.gson.Gson
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.home_dialog.view.*
import org.joda.time.DateTime
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

class DetailActivity : BaseActivity() {

    private val mColumAdapter = ColumAdapter()
    private val mList = ListAdapter()
    private var jobid: Int? = null
    private var listjob: Int? = null
    private var pricetec: Int? = null
    private var mCalendarstar: Calendar? = null

    private lateinit var viewModel: DetailViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val userId = baseContext?.getSharedPreferences(
            "file",
            AppCompatActivity.MODE_PRIVATE
        )?.getInt("id", 0)
        val idjob = intent.getIntExtra("orderid", 0)
        jobid = idjob
        val df = DecimalFormat("###,###.00")
        df.roundingMode = RoundingMode.CEILING
        var all: Int = 0

        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)


        viewModel.detail(idjob)
        viewModel.workjob.observe(this, { db ->
            val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
            val dateString = simpleDateFormat.format(db.date)
            tv_namejob_detail.text = db.typejob
            tv_date_detail.text = dateString
            tv_abode_detail.text =
                db.abode + "\nต. " + db.district_name + "\nอ. " + db.amphur_name + "\nจ. " + db.province_name
            tv_time_detail.text = db.timezone
            tv_repairlist_detail.text = db.repair_list
            tv_statusjob_detail.text = db.status
//            longitudeMap= db.longitude!!
//            latitudeMap= db.latitudeval!!
//            val req=ChekTec2(date = db.date!!,id_time = db.idtime!!)
//            viewModel.chek(req)

        })

        viewModel.pricetec.observe(this, {
            if (it != null) {
                all = it
                if (it != 0) {
                    tv_tec.text = df.format(it).toString() + " บาท"
                    pricetec = 1
                }
            } else if (it == null) {
                tv_tec.text = " "
            }

        })
        viewModel.sumprice.observe(this, { list ->
            if (list != null) {

                val sum: Int = list.sumOf { it.sum!! }
                val sum2: Int = sum.toInt() + all
                if (sum2 != 0) {
                    tv_sum.text = df.format(sum2) + " บาท"
                }

            }

        })

        viewModel.imgpayModel.observe(this, { Imag ->
            if (Imag.img == null) {
                iv_photo_money.setImageResource(R.drawable.bank)
                layoutpay.visibility=View.GONE
            } else if (Imag.img != null) {
                val baseUrl = Imag.img.toString()
                iv_photo_money.loadImageCircle(baseUrl)
            }



        })

        btadddatesum.setOnClickListener {
            val calendar = mCalendarstar ?: Calendar.getInstance()

            val year = calendar[Calendar.YEAR]
            val month = calendar[Calendar.MONTH]
            val day = calendar[Calendar.DAY_OF_MONTH]

            calendar.add(Calendar.DATE, 0)
            val dateDialog = DatePickerDialog(
                this,
                { view, year, month, dayOfMonth ->
//                        Toast.makeText(
//                                baseContext,
//                                "$year/${month.plus(1)}/$dayOfMonth",
//                                Toast.LENGTH_SHORT
//                        ).show()

                    tv_datesumjob_detail.visibility = View.VISIBLE
                    tv_datesumjob_detail.setText("$dayOfMonth/${month.plus(1)}/$year")
                    btadddatesum.visibility = View.GONE



                    val calendar = Calendar.getInstance().apply {
                        set(year, month, dayOfMonth)

                    }
                    mCalendarstar = calendar
                    val result=DateSumRequest(id = idjob,date = mCalendarstar!!.timeInMillis)
                    viewModel.confimdate(result)

                },
                year,
                month,
                day,
            )

            calendar.add(Calendar.DATE, 0)
            dateDialog.datePicker.minDate = DateTime.now().millis
//            dateDialog.datePicker.maxDate = DateTime.now().millis

//                .show()
            dateDialog.show()
        }

        btpricetec.setOnClickListener {

//            val dialogBuilder=AlertDialog.Builder(this)
//            dialogBuilder.setTitle("เพิ่มราคาค่าบริการ")
//            val view=layoutInflater.inflate(R.layout.home_dialog,null)
//            dialogBuilder.setView(view)
//            val alertDialog=dialogBuilder.create()
//            dialogBuilder.show()


            val mDialogView = LayoutInflater.from(this).inflate(R.layout.home_dialog, null)
            val mBuilder =
                AlertDialog.Builder(this).setView(mDialogView).setTitle("เพิ่มราคาค่าบริการ")
            val mAlertDialog = mBuilder.show()


            mDialogView.dialogOK.setOnClickListener {
                val texttec = mDialogView.dialog_text.text
                if (texttec.toString() != "") {
                    val req = PriceTecRequest(orderid = idjob, price = texttec.toString().toInt())
                    viewModel.pricetec(req)
                    mAlertDialog.dismiss()
                    tv_tec.text = df.format(texttec.toString().toInt()).toString() + " บาท"
                } else if (texttec.toString() == "") {
                    Toasty.Config.getInstance().setTextSize(30)
                    Toasty.warning(baseContext, "กรุณาใส่จำนวนเงิน", Toast.LENGTH_SHORT).show()
                }
            }
            mDialogView.dialogCancel.setOnClickListener {
                mAlertDialog.dismiss()
            }

        }

        viewModel.chekImg(idjob)

        btaddmat.setOnClickListener {
            val intent = Intent(baseContext, MaterialActivity::class.java).apply {
                putExtra("orderid", idjob)
//                val preferences = getSharedPreferences("fileidjob", Context.MODE_PRIVATE)
//                preferences.edit().putInt("id",idjob).apply()
            }
            startActivity(intent)
        }



        btoktec.setOnClickListener {
            viewModel.confim(idjob)
            val intent = Intent(baseContext, MainActivity::class.java)
            startActivity(intent)
            Toasty.Config.getInstance().setTextSize(30)
            Toasty.success(baseContext, "บันทึกงานสำเร็จ")

        }



        btcanceltec.setOnClickListener {
            viewModel.cancel(idjob)
            onBackPressed()
        }

         viewModel.chekstatusjob(idjob)
         viewModel.chekpricetec(idjob)
         viewModel.listdetail(idjob)




    }

    override fun onResume() {

        super.onResume()
        jobid?.let { viewModel.datetec(it) }
        jobid?.let { viewModel.chekstatusjob(it) }
        jobid?.let { viewModel.chekpricetec(it) }
        jobid?.let { viewModel.listdetail(it) }
        viewModel.datetec.observe(this,{datelong ->
            if (datelong ==0L){
                btadddatesum.visibility=View.VISIBLE
            }else{
                btadddatesum.visibility=View.GONE
                tv_datesumjob_detail.visibility=View.VISIBLE

                val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
                val dateString = simpleDateFormat.format(datelong)
                tv_datesumjob_detail.text=dateString
            }

        })
        viewModel.list.observe(this, { list ->
            if (list!=null){
                val adt = ConcatAdapter(
                    mColumAdapter, mList
                )
                recylerView.apply {
                    layoutManager = LinearLayoutManager(baseContext)
                    adapter = adt
                }
                mColumAdapter.submitData(Unit)
                mList.submitList(list)
                listjob = 1


                Log.d(TAG, "repair2: ${Gson().toJson(list)}")
                Log.d(TAG, "repair3: ${Gson().toJson(mList.submitList(list))}")
            }

            if (listjob == 1 && pricetec == 1) {
                btcanceltec.visibility = View.VISIBLE

                if (viewModel.statuspayjob.value == 2) {
                    btoktec.visibility = View.VISIBLE
                    btcanceltec.visibility = View.GONE
                    btfinishtec.visibility = View.GONE
                }

//                if (viewModel.statusjob.value == 2) {
//                    btoktec.visibility = View.GONE
//                    btcanceltec.visibility = View.GONE
////                btfinishtec.visibility = View.VISIBLE
//                } else if (viewModel.statusjob.value == 4) {
//                    btoktec.visibility = View.GONE
//                    btcanceltec.visibility = View.GONE
//                    btfinishtec.visibility = View.GONE
//                }
            }

        })



        jobid?.let { viewModel.chekfinish(it) }
        viewModel.finishjob.observe(this, { bd ->
            if (bd.sts == 3) {
                btfinishtec.isClickable=false
                btfinishtec.visibility = View.VISIBLE
                btoktec.visibility = View.GONE
                btcanceltec.visibility = View.GONE
                btfinishtec.setOnClickListener {
                    onBackPressed()
                    jobid?.let { it1 -> viewModel.finishjob(it1) }
                }
            }
        })




    }

    companion object {
        private const val TAG = "Detail"
    }
}