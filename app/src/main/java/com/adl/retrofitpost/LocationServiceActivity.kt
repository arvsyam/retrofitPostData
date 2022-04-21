package com.adl.retrofitpost

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.ActivityChooserView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.adl.retrofitpost.background.LocationService
import com.adl.retrofitpost.model.PostTrackingResponse
import com.adl.retrofitpost.model.SearchTrackingResponse
import com.adl.retrofitpost.service.RetrofitConfigTracking
import kotlinx.android.synthetic.main.activity_location_service.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LocationServiceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location_service)

        if(!checkPermission()){
            requestPermission()
        }

        btn_service.setOnClickListener({
            ContextCompat.startForegroundService(this, Intent(this,LocationService::class.java))
        })
        btn_stop_service.setOnClickListener({
            stopService(Intent(this,LocationService::class.java))
        })

        btn_search_tracker.setOnClickListener({
            searching()
        })




    }

    fun searching(){
        RetrofitConfigTracking().getService().searchTracker(et_seacrh_tracker.text.toString()).enqueue(object :
            Callback<SearchTrackingResponse> {
            override fun onResponse(
                call: Call<SearchTrackingResponse>,
                response: Response<SearchTrackingResponse>
            ) {
                if(response.isSuccessful()){
                    Log.d("response","${response.body()}")
                }else{
                    response.body().toString()
                    Log.d("response","failed to save")
                }
            }

            override fun onFailure(call: Call<SearchTrackingResponse>, t: Throwable) {
                Log.e("error request",t.localizedMessage,t)
            }

        })
    }

    fun checkPermission():Boolean{
        val fineLocationCheck = ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.ACCESS_FINE_LOCATION)
        val fineCoarseCheck = ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.ACCESS_COARSE_LOCATION)

        return fineLocationCheck == PackageManager.PERMISSION_GRANTED && fineCoarseCheck == PackageManager.PERMISSION_GRANTED
    }

    fun requestPermission(){
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION),1)
    }
}