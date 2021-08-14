package com.example.engineerapplication.presentation.technician.table

import android.view.View
import com.example.engineerapplication.R
import com.example.engineerapplication.base.BaseRecyclerView
import com.example.engineerapplication.data.models.OrderdetailModel
import kotlinx.android.synthetic.main.item_tablejob.view.*

class TablejobAdapter : BaseRecyclerView<OrderdetailModel>() {
    override fun getLayout(): Int = R.layout.item_tablejob
    override fun View.onBindViewHolder(data: OrderdetailModel) {
        tv_repair_list_table.text = "รายการซ่อม "+data.repair_List
        tv_adode_date_table.text ="ที่อยู่ " +data.adode
//        if (data.price == null) {
//            tv_price_table.text = "ราคา อยู่ระหว่างการประเมิน"
//        } else {
//            tv_price_table.text = "ราคา "+data.price.toString()
//        }
        tv_ststa_table.text ="สถานะงาน "+ data.status



    }

}