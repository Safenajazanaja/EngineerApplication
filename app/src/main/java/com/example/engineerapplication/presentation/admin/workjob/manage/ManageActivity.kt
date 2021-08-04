package com.example.engineerapplication.presentation.admin.workjob.manage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.engineerapplication.R
import com.example.engineerapplication.base.BaseActivity
import com.example.engineerapplication.data.models.ManageModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_manage.*
import java.text.SimpleDateFormat

class ManageActivity : BaseActivity(), OnMapReadyCallback {
    private var jobid: Int? = null
    private var mGoogleMap: GoogleMap? = null
    private var latitudeMap: Double = 0.0
    private var longitudeMap: Double = 0.0
    private lateinit var viewModel: ManageViewModel
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





        viewModel.workjob.observe(this, {db->
            val dateString = simpleDateFormat.format(db.date)
            tv_namejob_manage.text=db.typejob
            tv_date_manage.text=dateString
            tv_abode_manage.text=db.abode
            tv_time_manage.text=db.timezone
            tv_repairlist_manage.text=db.repair_list
            longitudeMap= db.longitude!!
            latitudeMap= db.latitudeval!!
        })


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
}