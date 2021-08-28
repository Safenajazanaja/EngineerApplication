package com.example.engineerapplication.presentation.history


import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.engineerapplication.R
import com.example.engineerapplication.base.BaseFragment
import com.example.engineerapplication.data.request.HistoryRequest
import com.example.engineerapplication.presentation.technician.detail.DetailActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_history.*
import org.joda.time.DateTime
import java.text.SimpleDateFormat
import java.util.*

class HistoryFragment : BaseFragment(R.layout.fragment_history){
    private var mCalendarstar: Calendar? = null
    private var mCalendarend: Calendar? = null
    var dateinmax: Long? = null
    private lateinit var viewModel: HistoryViewModel
    private lateinit var mHistoryAdapter: HistoryAdepter
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        val userId = context?.getSharedPreferences(
            "file",
            AppCompatActivity.MODE_PRIVATE
        )?.getInt("id", 0)

        viewModel = ViewModelProvider(this).get(HistoryViewModel::class.java)

        viewModel.toast.observe(this, { str ->
            Toast.makeText(requireContext(), "$str", Toast.LENGTH_SHORT).show()
        })


        //recyclerView1
        viewModel.history2.observe(this, { histories ->
//            val adt = HistoryAdapter()
//            recyclerViewDetail.apply {
//                layoutManager = LinearLayoutManager(context)
//                adapter = adt
//                //recyclerView2
////                viewModel.history2.observe(this,{histories2 ->
////                    val adt=HistoryAdapter()
////                    recyclerView.apply {
////                        layoutManager=LinearLayoutManager(context)
////                        adapter= adt
////                    }
////                    adt.setList(histories2)
////                })
//            }
//            adt.setList(histories)
//            adt.onClick={
//                val intent=Intent(context,DetailHistoryActivity::class.java).apply {
//                    val date=it.datelong
//                    putExtra("date",it.datelong)
//
//                }
//                startActivity(intent)
//            }
            mHistoryAdapter = HistoryAdepter()
            expandableListView.setAdapter(mHistoryAdapter)
            mHistoryAdapter.setList(histories)
            Log.d(TAG, "repair2: ${Gson().toJson(histories)}")
            mHistoryAdapter.setOnClickListener {
                val intent = Intent(context, DetailActivity::class.java).apply {
                    putExtra("orderid", it.orderid)
                }
                startActivity(intent)
            }
        })


//        val list = listOf(
//            HistoryModel2(
//                0,
//                "123",
//                0,
//                listOf(
//                    OrderModeldetail(
//                        1,
//                        "",
//                        "111",
//                        "",
//                        0
//                    ),
//                )
//            ),
//            HistoryModel2(
//                1,
//                "456",
//                1,
//                listOf(
//                    OrderModeldetail(
//                        1,
//                        "",
//                        "222",
//                        "",
//                        0
//                    ),
//                    OrderModeldetail(
//                        1,
//                        "",
//                        "333",
//                        "",
//                        0
//                    ),
//                )
//            ),
//        )


        Bt_history_datestar.setOnClickListener {
            val calendar = mCalendarstar ?: Calendar.getInstance()

            val year = calendar[Calendar.YEAR]
            val month = calendar[Calendar.MONTH]
            val day = calendar[Calendar.DAY_OF_MONTH]

            calendar.add(Calendar.DATE, 0)
            val dateDialog = DatePickerDialog(
                requireContext(),
                { view, year, month, dayOfMonth ->
                    Toast.makeText(
                        context,
                        "$year/${month.plus(1)}/$dayOfMonth",
                        Toast.LENGTH_SHORT
                    ).show()
                    Bt_history_datestar.setText("$year/${month.plus(1)}/$dayOfMonth")
                    Bt_history_dateend.visibility = View.VISIBLE

                    endText.visibility = View.VISIBLE


                    val calendar = Calendar.getInstance().apply {
                        set(year, month, dayOfMonth)

                    }
                    mCalendarstar = calendar
                    dateinmax = calendar.timeInMillis

                },
                year,
                month,
                day,
            )

            calendar.add(Calendar.DATE, 0)
            dateDialog.datePicker.maxDate = DateTime.now().millis

//                .show()
            dateDialog.show()


        }
        Bt_history_dateend.setOnClickListener {

            val calendar = mCalendarend ?: Calendar.getInstance()

            val year = calendar[Calendar.YEAR]
            val month = calendar[Calendar.MONTH]
            val day = calendar[Calendar.DAY_OF_MONTH]

            calendar.add(Calendar.DATE, 0)
            val dateDialog = DatePickerDialog(
                requireContext(),
                { view, year, month, dayOfMonth ->
                    Toast.makeText(
                        context,
                        "$year/${month.plus(1)}/$dayOfMonth",
                        Toast.LENGTH_SHORT
                    ).show()
                    Bt_history_dateend.setText("$year/${month.plus(1)}/$dayOfMonth")
                    Bt_history_ok.visibility = View.VISIBLE


                    val calendar = Calendar.getInstance().apply {
                        set(year, month, dayOfMonth)
                    }
                    mCalendarend = calendar

                },
                year,
                month,
                day,
            )

            calendar.add(Calendar.DATE, 0)
//            dateDialog.datePicker.maxDate = DateTime.now().millis
//            dateDialog.datePicker.minDate = dateinmax!!.toLong()

//                .show()
            dateDialog.show()
        }

        Bt_history_ok.setOnClickListener {
            val date1 = mCalendarstar!!.timeInMillis
            val date2 = mCalendarend!!.timeInMillis

            val sdf = SimpleDateFormat("dd/MM/yyyy")
            val dateStr1 = sdf.format(date1)
            val dateStr2 = sdf.format(date2)
            Log.d(TAG, "onActivityCreated: $dateStr1")
            Log.d(TAG, "onActivityCreated: $dateStr2")

            viewModel.repair(
                request = HistoryRequest(
                    id = userId!!,
                    star = mCalendarstar?.timeInMillis,
                    end = mCalendarend?.timeInMillis
                )
            )

        }


    }

    companion object {
        private const val TAG = "HistoryFragment"
    }

}