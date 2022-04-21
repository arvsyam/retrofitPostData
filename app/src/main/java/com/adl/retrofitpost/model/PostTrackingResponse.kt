package com.adl.retrofitpost.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PostTrackingResponse(
	val message: String? = null,
	val status: Boolean? = null
) : Parcelable
