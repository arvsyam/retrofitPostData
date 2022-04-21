package com.adl.retrofitpost.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SearchTrackingResponse(

	@field:SerializedName("total")
	val total: Int? = null,

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class Data(

	@field:SerializedName("tracking")
	val tracking: List<TrackingItem?>? = null
) : Parcelable

@Parcelize
data class TrackingItem(

	@field:SerializedName("latitude")
	val latitude: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("id_user")
	val idUser: String? = null,

	@field:SerializedName("longitude")
	val longitude: String? = null,

	@field:SerializedName("timestamp")
	val timestamp: String? = null
) : Parcelable
