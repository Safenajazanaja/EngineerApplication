package com.example.engineerapplication.presentation.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

import com.example.engineerapplication.R
import com.example.engineerapplication.base.BaseActivity
import com.example.engineerapplication.presentation.admin.history.HistoryFragment
import com.example.engineerapplication.presentation.admin.member.MemberFragment
import com.example.engineerapplication.presentation.admin.workjob.WorkjobFragment
import com.example.engineerapplication.presentation.technician.table.TablejobFragment
import com.example.engineerapplication.presentation.profile.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    private lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val userId = getSharedPreferences(
            "file",
            AppCompatActivity.MODE_PRIVATE
        )?.getInt("id", 0)

        viewModel=ViewModelProvider(this).get(MainViewModel::class.java)
        if (userId != null) {
            viewModel.chekrole(userId)
        }

        viewModel.id.observe(this,{id->
//            bottom_navigation.setOnNavigationItemSelectedListener(navListener)
            if (id==1) {
                bottom_navigation_admin.setOnNavigationItemSelectedListener(navListenerAdmin)
                bottom_navigation_admin.visibility = View.VISIBLE
                bottom_navigation_technician.visibility = View.GONE
            } else {
                bottom_navigation_technician.setOnNavigationItemSelectedListener(navListenerTechnician)
                bottom_navigation_technician.visibility = View.VISIBLE
                bottom_navigation_admin.visibility = View.GONE
            }
            if (savedInstanceState == null)
                replaceFragment(TablejobFragment())
        })


    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(
                R.id.fragment_container,
                fragment
        ).commit()
    }

    private val navListenerAdmin: BottomNavigationView.OnNavigationItemSelectedListener =
            BottomNavigationView.OnNavigationItemSelectedListener { item ->
                val selectedFragment: Fragment = when (item.itemId) {
                    R.id.nav_work -> WorkjobFragment()
                    R.id.nav_history -> HistoryFragment()
//                    R.id.nav_member -> MemberFragment()
                    else -> MemberFragment()
                }
                replaceFragment(selectedFragment)
                true
            }

    private val navListenerTechnician: BottomNavigationView.OnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            val selectedFragment: Fragment = when (item.itemId) {
                R.id.nav_job_you -> TablejobFragment()
                R.id.nav_history_mend -> HistoryFragment()
                else -> ProfileFragment()
            }
            replaceFragment(selectedFragment)
            true
        }
}