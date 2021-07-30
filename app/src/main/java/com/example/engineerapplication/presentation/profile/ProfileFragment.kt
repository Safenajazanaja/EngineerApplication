package com.example.engineerapplication.presentation.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.engineerapplication.R
import com.example.engineerapplication.base.BaseFragment
import com.example.engineerapplication.base.Dru
import com.example.engineerapplication.base.Dru.loadImageCircle
import com.example.engineerapplication.data.request.ImagsRequest
import kotlinx.android.synthetic.main.fragment_profile.*
import java.util.*

class ProfileFragment:BaseFragment(R.layout.fragment_profile) {
    private var mImageUri: Uri? = null
//    private var mImageUrl: Url? = null
    private  lateinit var viewModel: ProfileViewModel
    private var  user:Int?=null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val userId= context?.getSharedPreferences(
                "file",
                AppCompatActivity.MODE_PRIVATE
        )?.getInt("id",0)
        user=userId


        viewModel=ViewModelProvider(this).get(ProfileViewModel::class.java)
        viewModel.profile(userId)
        viewModel.profileModel.observe(this,{ profile ->
            tv_full_name_tec.text=profile.name.toString()
            tv_username_tec.text=profile.username.toString()
            tv_phone_tec.text=profile.telephone.toString()
            if (profile.img==null){
                iv_photo_tec.setImageResource(R.drawable.ic_user)
            }else if (profile.img!=null){
                val  baseUrl=profile.img.toString()
                iv_photo_tec.loadImageCircle(baseUrl)

            }

        })

        iv_photo_tec.setOnClickListener {
            val  intent=Intent()
            intent.type="image/*"
            intent.action=Intent.ACTION_GET_CONTENT
            startActivityForResult(intent,123)
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 123 && resultCode == AppCompatActivity.RESULT_OK && data != null){
            mImageUri=data.data
            iv_photo_tec.loadImageCircle(mImageUri.toString())
            val imageName=UUID.randomUUID().toString().replace("-","")+"jpg"
            val baseUrl="https://easyfix204.000webhostapp.com/image/"
            val  imagePath=baseUrl+imageName

            Dru.uploadImage(requireContext(),baseUrl,imageName,mImageUri){
                val  upimags= user?.let { ImagsRequest(it,imagePath)}
                if (upimags!=null){
                    viewModel.chekImg(upimags)
                }
            }
        }
    }
}