package com.example.engineerapplication.presentation.admin.workjob

import android.view.View
import com.example.engineerapplication.R
import com.example.engineerapplication.base.ExpandableListAdapter
import com.example.engineerapplication.data.models.HistoryModel2
import com.example.engineerapplication.data.models.OrderModeldetail
import com.example.engineerapplication.data.models.OrderaddModel
import com.example.engineerapplication.data.models.WorkaddTec2Model
import kotlinx.android.synthetic.main.item_history_date.view.*
import kotlinx.android.synthetic.main.item_single_item_main.view.*

class WorkjobAdepter : ExpandableListAdapter<WorkaddTec2Model, OrderaddModel>() {
    override fun getPropertyDetailList(item:WorkaddTec2Model): List<OrderaddModel> {
        return item.orders
    }

    override fun onCreateViewHolderMain(): Int = R.layout.item_single_item_main

    override fun View.onBindViewHolderMain(item: WorkaddTec2Model) {
        tv_date.text = "วันที่ " + item.date
        tv_datesum.text = "รวม " + item.sumOrderByDate.toString() + " รายการ"

    }

    override fun onCreateViewHolderDetail(): Int = R.layout.item_history_date

    override fun View.onBindViewHolderDetail(item: OrderaddModel) {
        tv_repair_list.text = item.repair_List
        tv_adode_date.text = item.adode
        if (item.price == null) {
            tv_price_his.text = "อยู่ระหว่างการประเมิน"
        } else {
            tv_price_his.text = item.price.toString()
        }
        tv_ststa.text = item.status
        setOnClickListener {
            listener?.invoke(item)
        }

    }

    private var listener: ((OrderaddModel) -> Unit)? = null

    fun setOnClickListener(listener: (OrderaddModel) -> Unit) {
        this.listener = listener
    }
}