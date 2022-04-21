package com.adl.retrofitpost.background

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat

class BootDeviceReceivers: BroadcastReceiver(){
    override fun onReceive(ctx: Context?, intent: Intent?) {
        ctx?.let{
            ContextCompat.startForegroundService(it,Intent(it,LocationService::class.java))
        }
    }
}