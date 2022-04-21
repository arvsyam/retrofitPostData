package com.adl.retrofitpost.background

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.location.Location
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.adl.retrofitpost.R
import com.adl.retrofitpost.model.GetIjinResponse
import com.adl.retrofitpost.model.PostTrackingResponse
import com.adl.retrofitpost.service.RetrofitConfig
import com.adl.retrofitpost.service.RetrofitConfigTracking
import com.adl.retrofitpost.utility.LocationHelper
import com.adl.retrofitpost.utility.MyLocationListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDate.now
import java.time.LocalDateTime.now
import java.util.*

class LocationService:Service() {

    companion object{
        var mLocation: Location?=null
        var isServiceStarted = false

    }


    private val NOTIFICATION_CHANNEL_ID = "notifikasi channel"
    private val TAG = "LOCATION_SERVICE"






    override fun onCreate() {
        super.onCreate()
        isServiceStarted = true

        val builder: NotificationCompat.Builder = NotificationCompat.Builder(this,NOTIFICATION_CHANNEL_ID)
            .setOngoing(false)
            .setSmallIcon(R.drawable.ic_launcher_background)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val notificationManager:NotificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            val notificationChannel= NotificationChannel(NOTIFICATION_CHANNEL_ID,NOTIFICATION_CHANNEL_ID,NotificationManager.IMPORTANCE_LOW)

            notificationChannel.description = NOTIFICATION_CHANNEL_ID
            notificationChannel.setSound(null,null)
            notificationManager.createNotificationChannel(notificationChannel)
            startForeground(1,builder.build())

        }

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
       LocationHelper().startListeningUserLocation(this,object:MyLocationListener{
           override fun onLocationChanged(location: Location) {
               if (isServiceStarted) {
                   val localDate = Calendar.getInstance()
                   val sdf = SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.ENGLISH)
                   val time = sdf.format(localDate.time)

                   Log.d("time", "${time}")

                   mLocation = location
                   mLocation?.let {
                       Log.d(
                           "SERVICE SEDANG BERJALAN LOKASINYA ADALAH",
                           " ${it?.longitude} -- ${it?.latitude}"
                       )
                   }
                   RetrofitConfigTracking().getService()
                       .addTrack("Pay", "${mLocation?.latitude}", "${mLocation?.longitude}", time)
                       .enqueue(object : Callback<PostTrackingResponse> {
                           override fun onResponse(
                               call: Call<PostTrackingResponse>,
                               response: Response<PostTrackingResponse>
                           ) {
                               if (response.isSuccessful()) {
                                   Log.d("response", "saved")
                               } else {
                                   response.body().toString()
                                   Log.d("response", "failed to save")
                               }
                           }

                           override fun onFailure(call: Call<PostTrackingResponse>, t: Throwable) {
                               Log.e("error request", t.localizedMessage, t)
                           }

                       })
               }
           }

       })

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        isServiceStarted = false
    }


    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }
}