package com.example.engineerapplication.presentation.material

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.engineerapplication.R
import com.example.engineerapplication.base.BaseActivity
import kotlinx.android.synthetic.main.activity_material.*

class MaterialActivity : BaseActivity() {
    private lateinit var viewModel: MaterialViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_material)
        val idjob=intent.getIntExtra("orderid",0)

        viewModel=ViewModelProvider(this).get(MaterialViewModel::class.java)
        viewModel.mater.observe(this,{ mat ->
            val adt = MaterialAdapter()
            recyclerViewmaterial.apply {
                layoutManager=LinearLayoutManager(context)
                adapter=adt
                val preferences = getSharedPreferences("fileidjob", Context.MODE_PRIVATE)
                preferences.edit().clear()
                preferences.edit().putInt("id",idjob).apply()


            }
            adt.setList(mat)




        })

        viewModel.material(idjob)
    }
}