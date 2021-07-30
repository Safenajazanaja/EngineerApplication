package com.example.engineerapplication.presentation.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.easyfix.data.datasource.DataSource
import com.example.engineerapplication.data.models.ProfileModel
import com.example.engineerapplication.data.request.ImagsRequest

class ProfileViewModel:ViewModel() {

    private  val  _profileModel=MutableLiveData<ProfileModel>()
    val  profileModel:LiveData<ProfileModel>
    get() = _profileModel

    fun profile(userId:Int?){
        _profileModel.value= userId?.let { DataSource.profile(it)}

    }

    fun chekImg(req: ImagsRequest) {
        DataSource.upimgprofile(req)
//        _profileModel.value=DataSource.profile(req.id)

        Log.d(TAG, "chekImg: ")

    }

    companion object{
        private const val TAG = "####"
    }
}