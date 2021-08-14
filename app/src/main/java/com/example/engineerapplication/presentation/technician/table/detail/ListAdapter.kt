package com.example.engineerapplication.presentation.technician.table.detail

import android.view.View
import com.example.engineerapplication.R
import com.example.engineerapplication.base.SimpleRecyclerView
import com.example.engineerapplication.data.models.ListModel
import kotlinx.android.synthetic.main.item_listitem.view.*
import java.math.RoundingMode
import java.text.DecimalFormat


class ListAdapter: SimpleRecyclerView<ListModel>() {
    override fun getLayout(): Int = R.layout.item_listitem
    override fun View.onBindViewHolder(currentData: ListModel, beforeData: ListModel?) {

        val df = DecimalFormat("###,###.00")
        df.roundingMode = RoundingMode.CEILING
        val price = currentData.qty
        val Unitprice=currentData.Unitprice
        tv_name.text=currentData.name
        tv_qty.text=currentData.qty.toString()
        tv_Unitprice.text= df.format(currentData.Unitprice).toString()
        val ob=price!! * Unitprice!!
        tv_price.text= df.format(ob).toString()

//        Log.d(TAG, "repair2: ${Gson().toJson(currentData)}")
    }

    companion object {
        private const val TAG = "adp"
    }
}