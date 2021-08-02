package com.example.engineerapplication.presentation.admin.workjob.manage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.engineerapplication.R
import com.example.engineerapplication.base.BaseActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class ManageActivity : BaseActivity(), OnMapReadyCallback {
    private var jobid: Int? = null
    private var mGoogleMap: GoogleMap? = null
    private var latitudeMap: Double = 0.0
    private var longitudeMap: Double = 0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage)


        val userId = baseContext?.getSharedPreferences(
            "file",
            AppCompatActivity.MODE_PRIVATE
        )?.getInt("id", 0)


        val userId2 = intent.getIntExtra("user_id", 0)
        val abode = intent.getStringExtra("abode")
        val repair_list = intent.getStringExtra("repair_list")
        val date = intent.getLongExtra("date", 0)
        val latitude = intent.getDoubleExtra("latitude", 0.0)
        val longitude = intent.getDoubleExtra("longitude", 0.0)
        val idtypejob = intent.getIntExtra("type_job", 0)
        val idtime=intent.getIntExtra("timejob",0)
        val timezone=intent.getStringExtra("timezone")
        val idjob = intent.getIntExtra("orderid", 0)
        jobid = idjob

        latitudeMap = latitude.toDouble()
        longitudeMap = longitude.toDouble()


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