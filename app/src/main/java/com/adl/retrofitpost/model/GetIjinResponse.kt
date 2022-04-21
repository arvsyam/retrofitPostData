package com.adl.retrofitpost.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GetIjinResponse(

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
data class IjinItem(

	@field:SerializedName("keterangan")
	val keterangan: String? = null,

	@field:SerializedName("lampiran")
	val lampiran: String? = null,

	@field:SerializedName("kategori")
	val kategori: String? = null,

	@field:SerializedName("dari")
	val dari: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("perihal")
	val perihal: String? = null,

	@field:SerializedName("sampai")
	val sampai: String? = null
) : Parcelable

@Parcelize
data class Dataa(  // must be data not dataa

	@field:SerializedName("ijin")
	val ijin: List<IjinItem?>? = null
) : Parcelable
