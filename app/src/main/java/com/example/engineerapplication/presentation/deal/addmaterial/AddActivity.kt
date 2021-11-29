package com.example.engineerapplication.presentation.deal.addmaterial

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.engineerapplication.R
import com.example.engineerapplication.base.BaseActivity
import com.example.engineerapplication.data.request.AddMaterialRequest
import com.example.engineerapplication.data.request.AddRequest
import com.example.engineerapplication.presentation.admin.member.TraceMemberFragment
import com.example.engineerapplication.presentation.admin.workjob.WorkjobFragment
import com.example.engineerapplication.presentation.deal.DealFragment
import com.example.engineerapplication.presentation.history.HistoryFragment
import com.example.engineerapplication.presentation.main.MainViewModel
import com.example.engineerapplication.presentation.profile.ProfileFragment
import com.example.engineerapplication.presentation.technician.TablejobFragment
import com.example.loginmvvm.presentation.history.detail.ColumAdapter
import com.example.loginmvvm.presentation.history.detail.ColumMaterAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_material.*
import kotlinx.android.synthetic.main.mater_dialog.*

class AddActivity : BaseActivity() {
    private val mColumAdapter = ColumMaterAdapter()
    private val mListAdapter = ListMaterAdapter()
    private lateinit var viewModel: AddViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        val userId = getSharedPreferences(
            "file",
            AppCompatActivity.MODE_PRIVATE
        )?.getInt("id", 0)

        viewModel = ViewModelProvider(this).get(AddViewModel::class.java)


        viewModel.toast.observe(this, { str ->
            Toasty.Config.getInstance().setTextSize(30)
            Toasty.warning(baseContext, "$str", Toast.LENGTH_SHORT).show()
        })
        viewModel.mater()

        val adt = ConcatAdapter(
            mColumAdapter, mListAdapter
        )
        recylerViewmat.apply {
            layoutManager = LinearLayoutManager(baseContext)
            adapter = adt
        }
        mColumAdapter.submitData(Unit)
        mListAdapter.submitList(viewModel.materlist.value!!)

//        bt_ok_material.setOnClickListener {
//
//            val materialname = etNamematerial.text.toString()
//            val materialprice = etPaymaterial.text.toString()
//            val req = AddMaterialRequest(materialname = materialname, price = materialprice)
//            viewModel.add(req)
//            onBackPressed()
//        }
//
//        bt_cancel_material.setOnClickListener {
//            onBackPressed()
//        }


        editmaterialButton.setOnClickListener {
            //Inflate the dialog with custom view
            val mDialogView = LayoutInflater.from(this).inflate(R.layout.mater_dialog, null)
            //AlertDialogBuilder
            val mBuilder =
                AlertDialog.Builder(this).setView(mDialogView)
            //show dialog
            val mAlertDialog = mBuilder.show()

            mAlertDialog.mat_ebt_cancel_material.setOnClickListener {
                mAlertDialog.dismiss()
                this.recreate()
            }

            mAlertDialog.mat_ebt_ok_material.setOnClickListener {

                val materialname = mAlertDialog.mat_etNamematerial.text.toString()
                val materialprice = mAlertDialog.mat_etPaymaterial.text.toString()
                val req = AddMaterialRequest(materialname = materialname, price = materialprice)
                viewModel.add(req)
               viewModel.success.observe(this,{b ->
                   if (b){
                       Toasty.Config.getInstance().setTextSize(30)
                       Toasty.success(baseContext,"บันทึกข้อมูลสำเสร็จ",Toast.LENGTH_SHORT).show()
                       this.recreate()

                   }else{
                       Toasty.Config.getInstance().setTextSize(30)
                       Toasty.warning(baseContext,"ชื่อวัสดุก่อสร้างซ้ำ",Toast.LENGTH_SHORT).show()
                   }
               })

            }


        }
    }

}
