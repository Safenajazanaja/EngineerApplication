package com.example.engineerapplication.presentation.admin.workjob.manage

import android.content.Context
import android.view.View
import com.example.engineerapplication.R
import com.example.engineerapplication.base.BaseSpinner
import com.example.engineerapplication.data.models.Technician1Model
import kotlinx.android.synthetic.main.item_spinner_base.view.*

class SpinnerTecAdapter(context: Context, list: MutableList<Technician1Model>):
    BaseSpinner<Technician1Model>(context,list) {
    override fun getLayout(): Int = R.layout.item_spinner_base

    override fun View.onBindViewHolder(data: Technician1Model) {
      tvSpinnerBase.text=data.name
    }
}