package com.example.engineerapplication.presentation.technician.table.detail


import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.engineerapplication.R
import com.example.engineerapplication.base.BaseActivity
import com.example.engineerapplication.base.Dru
import com.example.engineerapplication.base.Dru.loadImageCircle
import com.example.engineerapplication.data.request.ImagsRequest
import com.example.engineerapplication.data.request.PriceTecRequest
import com.example.engineerapplication.presentation.material.MaterialActivity
import com.example.loginmvvm.presentation.history.detail.ColumAdapter

import com.google.gson.Gson
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.home_dialog.view.*
import java.math.RoundingMode
import java.text.DecimalFormat


import java.util.*

class DetailActivity : BaseActivity() {

    private val mColumAdapter = ColumAdapter()
    private val mList = ListAdapter()
    private var jobid: Int? = null

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

        viewModel.list.observe(this, { list ->
            val adt = ConcatAdapter(
                mColumAdapter, mList
            )
            recylerView.apply {
                layoutManager = LinearLayoutManager(baseContext)
                adapter = adt
            }
            mColumAdapter.submitData(Unit)
            mList.submitList(list)
            Log.d(TAG, "repair2: ${Gson().toJson(list)}")
            Log.d(TAG, "repair3: ${Gson().toJson(mList.submitList(list))}")
        })


        viewModel.chekpricetec(idjob)
        viewModel.listdetail(idjob)
        viewModel.pricetec.observe(this, {
            if (it != null) {
                all=it
                if (it!=0){
                    tv_tec.text = df.format(it).toString() + " บาท"
                }

            }else if (it==null){
                tv_tec.text = " "
            }

        })
        viewModel.sumprice.observe(this, { list ->
            if (list != null) {

                val sum: Int = list.sumOf { it.sum!! }
                val sum2: Int = sum.toInt() + all
                if (sum2!=0){
                    tv_sum.text =df.format(sum2)+ " บาท"
                }

            }

        })

        viewModel.imgpayModel.observe(this, { Imag ->
            if (Imag.img == null) {
                iv_photo_money.setImageResource(R.drawable.bank)
            } else if (Imag.img != null) {
                val baseUrl = Imag.img.toString()
                iv_photo_money.loadImageCircle(baseUrl)
            }

        })

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
                if (texttec != null) {
                    val req = PriceTecRequest(orderid = idjob, price = texttec.toString().toInt())
                    viewModel.pricetec(req)
                    mAlertDialog.dismiss()
                    tv_tec.text = df.format(texttec).toString()+ " บาท"
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

    }

    companion object {
        private const val TAG = "Detail"
    }
}