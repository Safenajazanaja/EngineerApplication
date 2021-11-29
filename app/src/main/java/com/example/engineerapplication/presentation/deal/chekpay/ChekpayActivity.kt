package com.example.engineerapplication.presentation.deal.chekpay

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.engineerapplication.R
import com.example.engineerapplication.base.BaseActivity
import com.example.engineerapplication.presentation.deal.chekpay.detail.DetailPayActivity
import com.example.engineerapplication.presentation.technician.detail.DetailActivity
import kotlinx.android.synthetic.main.activity_chekpay.*

class ChekpayActivity : BaseActivity() {

    private lateinit var viewModel: ChekpayViewModel

    private lateinit var mPayAdapter: PayAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chekpay)
        val userId = baseContext.getSharedPreferences(
            "file",
            AppCompatActivity.MODE_PRIVATE
        )?.getInt("id", 0)

        viewModel=ViewModelProvider(this).get(ChekpayViewModel::class.java)
        viewModel.chekpat()

        viewModel.history2.observe(this,{pay ->
            if (pay==null){

            }else{
                mPayAdapter= PayAdapter()
                expandableListViewPay.setAdapter(mPayAdapter)
                mPayAdapter.setList(pay)
                mPayAdapter.setOnClickListener {
                    val intent = Intent(baseContext, DetailPayActivity::class.java).apply {
                        putExtra("orderid", it.orderid)
                    }
                    startActivity(intent)
                }
            }

        })

    }

    override fun onRestart() {
        super.onRestart()
        this.recreate()
    }


}