package com.adl.retrofitpost.utility

import android.location.Location

interface MyLocationListener {
    fun onLocationChanged(location:Location)
}