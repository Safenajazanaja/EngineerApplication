package com.example.engineerapplication.presentation.deal

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.engineerapplication.R
import com.example.engineerapplication.base.BaseFragment
import com.example.engineerapplication.presentation.deal.addmaterial.AddActivity
import com.example.engineerapplication.presentation.deal.chekpay.ChekpayActivity
import kotlinx.android.synthetic.main.fragment_deal.*

class DealFragment : BaseFragment(R.layout.fragment_deal) {
    private var  user:Int?=null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val userId= context?.getSharedPreferences(
            "file",
            AppCompatActivity.MODE_PRIVATE
        )?.getInt("id",0)

        bt_addmaterial.setOnClickListener {
            val intent= Intent(context, AddActivity::class.java)
            startActivity(intent)
        }
        bt_chekpay.setOnClickListener {
            val intent= Intent(context, ChekpayActivity::class.java)
            startActivity(intent)
        }
    }
}