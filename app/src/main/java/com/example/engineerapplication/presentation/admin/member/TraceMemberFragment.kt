package com.example.engineerapplication.presentation.admin.member

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.engineerapplication.R
import com.example.engineerapplication.base.BaseFragment
import com.example.engineerapplication.presentation.technician.table.detail.DetailActivity
import kotlinx.android.synthetic.main.fragment_trace_member.*

class TraceMemberFragment:BaseFragment(R.layout.fragment_trace_member) {

    private lateinit var viewModel: TraceMemberViewModel
    private lateinit var mTraceMemberAdepter: TraceMemberAdepter
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this).get(TraceMemberViewModel::class.java)
        val userId =
            context?.getSharedPreferences("file",
                AppCompatActivity.MODE_PRIVATE
            )?.getInt("id",0)

        viewModel.tracejob()

        viewModel.trace.observe(this,{ mem ->
            mTraceMemberAdepter= TraceMemberAdepter()
            expandableListViewmembertrace.setAdapter(mTraceMemberAdepter)

            mTraceMemberAdepter.setList(mem)
            mTraceMemberAdepter.setOnClickListener {
                val intent=Intent(context,DetailActivity::class.java).apply {
                    putExtra("orderid",it.orderid)
                }
                startActivity(intent)
            }
        })
    }
}


