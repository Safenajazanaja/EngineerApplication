package com.example.engineerapplication.presentation.deal.addmaterial

import android.view.View
import com.example.engineerapplication.R
import com.example.engineerapplication.base.SimpleRecyclerView
import com.example.engineerapplication.data.models.ListMaterialModel
import kotlinx.android.synthetic.main.item_add_material.view.*
import java.math.RoundingMode
import java.text.DecimalFormat


class ListMaterAdapter : SimpleRecyclerView<ListMaterialModel>() {
    override fun getLayout(): Int = R.layout.item_add_material


    companion object {
        private const val TAG = "adp"
    }
    override fun View.onBindViewHolder(currentData: ListMaterialModel, beforeData: ListMaterialModel?) {
        val df = DecimalFormat("###,###.00")
        df.roundingMode = RoundingMode.CEILING
        matername.text=currentData.materialname
        materprice.text=df.format(currentData.materialprice).toString()
    }
}