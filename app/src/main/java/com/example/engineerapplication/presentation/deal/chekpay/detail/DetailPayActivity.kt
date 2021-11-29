package com.example.engineerapplication.presentation.deal.chekpay.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.engineerapplication.R
import com.example.engineerapplication.base.BaseActivity
import com.example.engineerapplication.base.Dru.loadImageCircle
import com.example.engineerapplication.data.database.Orderl.status
import com.example.engineerapplication.data.request.ChekTec2
import com.example.engineerapplication.data.request.UpdatePayRequest
import com.example.engineerapplication.presentation.deal.chekpay.ChekpayActivity
import com.example.engineerapplication.presentation.main.DealMainActivity
import com.example.engineerapplication.presentation.main.MainActivity
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_detail_pay.*
import kotlinx.android.synthetic.main.activity_manage.*
import kotlinx.android.synthetic.main.activity_manage.tv_abode_manage
import kotlinx.android.synthetic.main.activity_manage.tv_date_manage
import kotlinx.android.synthetic.main.activity_manage.tv_namejob_manage
import kotlinx.android.synthetic.main.activity_manage.tv_repairlist_manage
import java.text.SimpleDateFormat

class DetailPayActivity : BaseActivity() {
    private lateinit var viewModel: DetailPayViewModel
    var status = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_pay)


        viewModel = ViewModelProvider(this).get(DetailPayViewModel::class.java)

        val idjob = intent.getIntExtra("orderid", 0)


        viewModel.workjob.observe(this, { db ->
            val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
            val dateString = simpleDateFormat.format(db.date)
            tv_namejob_pay.text = db.repair_list
            tv_date_pay.text = dateString
            tv_abode_pay.text =
                db.abode + "\nต." + db.district_name + "อ." + db.amphur_name + "จ." + db.province_name
            tv_repairlist_pay.text = db.typejob
            val req = ChekTec2(date = db.date!!, id_time = db.idtime!!)

        })

        viewModel.imgpayModel.observe(this, { Imag ->
            if (Imag.img == null) {
                iv_photo_money.setImageResource(R.drawable.bank)
            } else if (Imag.img != null) {
                val baseUrl = Imag.img.toString()
                iv_photo_pay.loadImageCircle(baseUrl)
            }

        })
        viewModel.mana(idjob)
        viewModel.chekImg(idjob)

        bt_cancel_pay.setOnClickListener {
//            val intent = Intent(baseContext, MainActivity::class.java)
//            startActivity(intent)
            onBackPressed()
        }


        radioButton1.setOnClickListener {
            status = 0
            status = 2
            bt_confirm__pay.isEnabled = true
        }
        radioButton2.setOnClickListener {
            status = 0
            status = 4
            bt_confirm__pay.isEnabled = true
        }
        bt_confirm__pay.setOnClickListener {
            val req=UpdatePayRequest(orderid =idjob,payid = status )
            viewModel.updatepay(req)
//            val intent = Intent(baseContext, MainActivity::class.java)
//            startActivity(intent)
            Toasty.Config.getInstance().setTextSize(30)
            Toasty.success(baseContext,"บันทึกสำเสร็จ",Toast.LENGTH_SHORT).show()
            onBackPressed()

        }
    }
}