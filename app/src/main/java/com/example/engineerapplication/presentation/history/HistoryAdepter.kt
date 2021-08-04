package com.example.engineerapplication.presentation.history

import android.view.View
import com.example.engineerapplication.R
import com.example.engineerapplication.base.ExpandableListAdapter
import com.example.engineerapplication.data.models.HistoryModel2
import com.example.engineerapplication.data.models.OrderModeldetail
import kotlinx.android.synthetic.main.item_history_date.view.*
import kotlinx.android.synthetic.main.item_single_item_main.view.*

class HistoryAdepter : ExpandableListAdapter<HistoryModel2, OrderModeldetail>() {
    override fun getPropertyDetailList(item: HistoryModel2): List<OrderModeldetail> {
        return item.orders
    }

    override fun onCreateViewHolderMain(): Int = R.layout.item_single_item_main

    override fun View.onBindViewHolderMain(item: HistoryModel2) {
        tv_date.text = "วันที่ " + item.date
        tv_datesum.text = "รวม " + item.sumOrderByDate.toString() + " รายการ"
    }

    override fun onCreateViewHolderDetail(): Int = R.layout.item_history_date

    override fun View.onBindViewHolderDetail(item: OrderModeldetail) {
        tv_repair_list.text = item.repair_List
        tv_adode_date.text = item.adode
//        if (item.price == null) {
//            tv_price_his.text = "อยู่ระหว่างประเมินราคา"
//        } else {
//            tv_price_his.text = item.price.toString()
//        }

        tv_ststa.text = item.status


        setOnClickListener {
            listener?.invoke(item)
        }
    }


    private var listener: ((OrderModeldetail) -> Unit)? = null

    fun setOnClickListener(listener: (OrderModeldetail) -> Unit) {
        this.listener = listener
    }
}