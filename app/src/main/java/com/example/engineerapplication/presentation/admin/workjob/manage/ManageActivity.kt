package com.example.engineerapplication.presentation.admin.workjob.manage

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.example.easyfix.data.datasource.DataSource
import com.example.engineerapplication.R
import com.example.engineerapplication.base.BaseActivity
import com.example.engineerapplication.base.onItemSelected
import com.example.engineerapplication.data.models.ManageModel
import com.example.engineerapplication.data.models.Technician1Model
import com.example.engineerapplication.data.request.AddRequest
import com.example.engineerapplication.data.request.ChekTec2
import com.example.engineerapplication.data.request.ConfimtecRequest
import com.example.engineerapplication.presentation.main.MainActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_manage.*
import kotlinx.android.synthetic.main.tec_dialog.view.*
import java.text.SimpleDateFormat

class ManageActivity : BaseActivity(), OnMapReadyCallback {
    private var jobid: Int? = null
    private var mGoogleMap: GoogleMap? = null
    private var latitudeMap: Double = 0.0
    private var longitudeMap: Double = 0.0
    private lateinit var viewModel: ManageViewModel

    private lateinit var type: Technician1Model
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage)

        viewModel = ViewModelProvider(this).get(ManageViewModel::class.java)


        val userId = baseContext?.getSharedPreferences(
            "file",
            AppCompatActivity.MODE_PRIVATE
        )?.getInt("id", 0)

        val idjob = intent.getIntExtra("orderid", 0)


        val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy")


        viewModel.mana(idjob)





        viewModel.workjob.observe(this, { db ->
            val dateString = simpleDateFormat.format(db.date)
            tv_namejob_manage.text = db.repair_list
            tv_date_manage.text = dateString
            tv_abode_manage.text = db.abode
            tv_time_manage.text = db.timezone
            tv_repairlist_manage.text = db.typejob
            longitudeMap = db.longitude!!
            latitudeMap = db.latitudeval!!
            val req = ChekTec2(date = db.date!!, id_time = db.idtime!!)
            viewModel.chek(req)

        })

        viewModel.chektec.observe(this, { list ->
            Log.d(TAG, "add22: $list")
            bar_spinner_tecjob.adapter = SpinnerTecAdapter(
                this,
                list as MutableList<Technician1Model>
            )
            bar_spinner_tecjob.onItemSelected<Technician1Model> {
                type = it
                bt_cancel_manage.isEnabled = true
            }


        })

        bt_cancel_manage.isEnabled = false

        bt_cancel_manage.setOnClickListener {
            val intent = Intent(baseContext, MainActivity::class.java)
            startActivity(intent)
        }
        bt_confirm_manage.setOnClickListener {
            val add = ConfimtecRequest(id_job = idjob, id_tec = type.id!!)
            viewModel.confim(add)
            Toasty.Config.getInstance().setTextSize(30)
            Toasty.success(baseContext,"บันทึกสำเร็จ", Toast.LENGTH_SHORT).show()
            val intent = Intent(baseContext, MainActivity::class.java)
            startActivity(intent)
        }

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.maps) as SupportMapFragment
        mapFragment.getMapAsync(this)


    }

    override fun onMapReady(googleMap: GoogleMap?) {
        mGoogleMap = googleMap
        val latLng = LatLng(latitudeMap, longitudeMap)
        mGoogleMap?.addMarker(MarkerOptions().position(latLng).title("บ้าน"))
        mGoogleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))

    }

    companion object {
        private const val TAG = "ManageActivity"
    }
}