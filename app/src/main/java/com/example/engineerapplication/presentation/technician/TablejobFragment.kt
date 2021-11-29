package com.example.engineerapplication.presentation.technician

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.engineerapplication.R
import com.example.engineerapplication.base.BaseFragment
import com.example.engineerapplication.presentation.technician.detail.DetailActivity
import kotlinx.android.synthetic.main.fragment_tec_tablejob.*

class TablejobFragment : BaseFragment(R.layout.fragment_tec_tablejob) {
    private lateinit var viewModel: TableViewModel
    private var id: Int? = null
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val userid = context?.getSharedPreferences(
            "file",
            AppCompatActivity.MODE_PRIVATE
        )?.getInt("id", 0)

        viewModel = ViewModelProvider(this).get(TableViewModel::class.java)

        id = userid
//        userid?.let { viewModel.tablejob(it) }
//
//        viewModel.tablejob.observe(this, { tablejob ->
//            val adt = TablejobAdapter()
//            recycler_view.apply {
//                layoutManager = LinearLayoutManager(context)
//                adapter = adt
//            }
//            adt.setList(tablejob)
//
//            adt.onClick = {
//                val intent = Intent(context, DetailActivity::class.java).apply {
//                    putExtra("orderid", it.orderid)
//                }
//                startActivity(intent)
//
//            }
//        })


    }

    override fun onResume() {
        super.onResume()
        id?.let { viewModel.tablejob(it) }

        viewModel.tablejob.observe(this, { tablejob ->
            val adt = TablejobAdapter()
            recycler_view.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = adt
            }
            adt.setList(tablejob)

            adt.onClick = {
                val intent = Intent(context, DetailActivity::class.java).apply {
                    putExtra("orderid", it.orderid)
                }
                startActivity(intent)

            }
        })
    }
}