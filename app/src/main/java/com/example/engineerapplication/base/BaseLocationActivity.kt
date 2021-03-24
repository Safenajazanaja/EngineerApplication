package com.example.engineerapplication.base

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings

abstract class BaseLocationActivity : BaseActivity() {

    private val broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (LocationManager.PROVIDERS_CHANGED_ACTION == intent.action) {
                val locationManager =
                    context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
                val isGpsEnabled =
                    locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) //NETWORK_PROVIDER

                if (!isGpsEnabled)
                    settingLocation()
            }
        }
    }
     override fun onCreate(savedInstanceState: Bundle?) {
         super.onCreate(savedInstanceState)
         settingLocation()
     }

     override fun onResume() {
         super.onResume()
         //Register receiver.
         broadcastReceiver(true)
     }

     override fun onPause() {
         super.onPause()
         //Unregister receiver.
         broadcastReceiver(false)
     }

     // When location is not enabled, the application will end.
     override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
         super.onActivityResult(requestCode, resultCode, data)
         if (!isLocationProviderEnabled() && requestCode == 999)
             finishAffinity()
     }

     // Set up receiver register & unregister.
     private fun broadcastReceiver(isReceiver: Boolean) {
         if (isReceiver) {
             val filter = IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION)
             filter.addAction(Intent.ACTION_PROVIDER_CHANGED)
             registerReceiver(broadcastReceiver, filter)
         } else {
             unregisterReceiver(broadcastReceiver)
         }
     }

     // If location off give on setting on.
     private fun settingLocation() {
         if (!isLocationProviderEnabled()) {
             Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS).apply {
                 startActivityForResult(this, 999)
             }
         }
     }

     private fun isLocationProviderEnabled(): Boolean {
         return Settings.Secure.isLocationProviderEnabled(
             baseContext.contentResolver,
             LocationManager.GPS_PROVIDER
         )
     }

 }