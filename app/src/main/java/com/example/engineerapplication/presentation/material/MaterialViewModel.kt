package com.example.engineerapplication.presentation.material

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.easyfix.data.datasource.DataSource
import com.example.engineerapplication.data.models.SetViewMatialModel
import com.google.gson.Gson

class MaterialViewModel: ViewModel()  {

    private var _toast = MutableLiveData<String>()
    val toast: LiveData<String>
        get() = _toast


    private var _mater = MutableLiveData<List<SetViewMatialModel>>()
    val mater: LiveData<List<SetViewMatialModel>>
        get() = _mater

    fun  material(idjob:Int){
        val result=DataSource.setviewmaterial(idjob)
        _mater.value=result

        Log.d(TAG, "MAT: ${Gson().toJson(result)}")

    }

    companion object{
        private const val TAG="MaT"
    }
}