package com.example.engineerapplication.presentation.material

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.easyfix.data.datasource.DataSource
import com.example.engineerapplication.R
import com.example.engineerapplication.base.BaseRecyclerView
import com.example.engineerapplication.data.models.SetViewMatialModel
import com.example.engineerapplication.data.request.AddRequest
import com.example.engineerapplication.data.request.CkkRequest
import kotlinx.android.synthetic.main.item_material.view.*
import java.math.RoundingMode
import java.text.DecimalFormat

class MaterialAdapter : BaseRecyclerView<SetViewMatialModel>() {
    override fun getLayout(): Int = R.layout.item_material
    override fun View.onBindViewHolder(data: SetViewMatialModel) {

        val idjob = context?.getSharedPreferences(
            "fileidjob",
            AppCompatActivity.MODE_PRIVATE
        )?.getInt("id", 0)

        var sum = 0
        val req = CkkRequest(idjob = idjob, idmaterial = data.id)
        val ckk = DataSource.ckek(req)
        var sumall = 0

        val df = DecimalFormat("###,###.00")
        df.roundingMode = RoundingMode.CEILING

        if (ckk.success == true) {
            tv_name_material.text = ckk.name
            tv_Unitprice_material.text = df.format(ckk.price).toString() + " บาท"
            sum = ckk.qty!!.toInt()
            integer_number.text = sum.toString()
            val form = sum!! * data.price!!
            tv_price_material.text = df.format(form).toString() + " บาท"
            sumall = sum

        } else {
            tv_name_material.text = data.name
            tv_Unitprice_material.text = data.price.toString()
            tv_price_material.text = "0.00 บาท"
            integer_number.text = sum.toString()
            sumall = sum

        }

        btn_added.setOnClickListener {
            if (ckk.success == true) {
                val add = AddRequest(orderid = idjob, materialid = ckk.id, qty = sumall)
                DataSource.add(add)
            } else {
                if (sumall != 0) {
                    val add = AddRequest(orderid = idjob, materialid = data.id, qty = sumall)
                    DataSource.addnew(add)
                }

            }
        }








        decrease.setOnClickListener {
            if (sum != 0) {
                if (sum == 1) {
                    if (sum != null) {
                        sum = sum!! - 1
                        integer_number.text = sum.toString()
                        val form = sum!! * data.price!!
                        tv_price_material.text = "0.00 บาท"
                        sumall = sum
                    }
                } else  {
                        sum = sum!! - 1
                        integer_number.text = sum.toString()
                        val form = sum!! * data.price!!
                        tv_price_material.text = form.toString() + ".00 บาท"
                        sumall = sum
                    }
            }

        }
        increase.setOnClickListener {
            sum = sum!! + 1
            integer_number.text = sum.toString()
            val form = sum!! * data.price!!
            tv_price_material.text = df.format(form).toString() + " บาท"
            sumall = sum

        }
    }
}