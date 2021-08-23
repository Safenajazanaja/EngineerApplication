package com.example.engineerapplication.presentation.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.engineerapplication.R
import com.example.engineerapplication.base.BaseActivity
import com.example.engineerapplication.data.request.LoginRequest
import com.example.engineerapplication.presentation.main.MainActivity
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() {
    private lateinit var viewModel: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        viewModel.toast.observe(this, { str ->
            Toast.makeText(baseContext, "$str", Toast.LENGTH_SHORT).show()
        })

        viewModel.login.observe(this, { b ->
            if (b) {
                viewModel.id.observe(this,{a ->

                    val preferences = getSharedPreferences("file", Context.MODE_PRIVATE)
                    viewModel.id.let { preferences.edit().putInt("id",a).apply() }
                    Toasty.Config.getInstance().setTextSize(30)
                    Toasty.success(baseContext,"เข้าสู่ระบบสำเร็จ", Toast.LENGTH_SHORT).show()

                    val intent= Intent(baseContext, MainActivity::class.java).putExtra("id",a)
                    startActivity(intent)

                })

            } else {
                Toasty.Config.getInstance().setTextSize(30)
                Toasty.error(baseContext,"ตรวจสอบอีกครั้ง", Toast.LENGTH_SHORT).show()
            }
        })

        btlogin.setOnClickListener {
            val Username = etUsername.text.toString().trim()
            val Password = etPassword.text.toString().trim()
            val Login = LoginRequest(Username, Password)
            viewModel.login(Login)

        }




    }
}