package com.example.engineerapplication.presentation.admin.workjob

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.engineerapplication.R
import com.example.engineerapplication.base.BaseFragment
import com.example.engineerapplication.presentation.admin.workjob.manage.ManageActivity
import kotlinx.android.synthetic.main.activity_manage.*
import kotlinx.android.synthetic.main.fragment_manage.*
import kotlinx.android.synthetic.main.fragment_trace_member.*
import kotlin.reflect.jvm.internal.impl.protobuf.Internal

class WorkjobFragment: BaseFragment(R.layout.fragment_manage) {
    private lateinit var viewModel: WorkjobViewmodel
    private  lateinit var mWorkjobAdepter: WorkjobAdepter
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel=ViewModelProvider(this).get(WorkjobViewmodel::class.java)

        viewModel.workjob()

        viewModel.work.observe(this,{ work ->
            mWorkjobAdepter=WorkjobAdepter()
            expandableaddtecView.setAdapter(mWorkjobAdepter)
            mWorkjobAdepter.setList(work)
            mWorkjobAdepter.setOnClickListener {
                val intent=Intent(context,ManageActivity::class.java).apply {
                    putExtra("orderid",it.orderid)
                }
                startActivity(intent)
            }


        })

    }
}