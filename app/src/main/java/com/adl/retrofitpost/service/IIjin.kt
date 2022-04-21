package com.adl.retrofitpost.service

import com.adl.retrofitpost.model.GetIjinResponse
import com.adl.retrofitpost.model.IjinItem
import com.adl.retrofitpost.model.PostIjinResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface IIjin {

    @Headers("X-Api-Key:FC35B899DC137F0CB6B56035FA37068A")
    @GET("api/ijin/all")
    fun getAll(): Call<GetIjinResponse>

    @FormUrlEncoded
    @Headers("X-Api-Key:FC35B899DC137F0CB6B56035FA37068A")
    @POST("api/ijin/add")
    fun addNewIjin(@Field("kategori")kategori :String, @Field("dari") dari:String, @Field("sampai") sampai:String,@Field("perihal") perihal:String, @Field("keterangan") keterangan:String):Call<PostIjinResponse>



    @Multipart
    @Headers("X-Api-Key:FC35B899DC137F0CB6B56035FA37068A")
    @POST("api/ijin/add")
    fun addDataWithImage(@Part("kategori") kategori: RequestBody, @Part("dari") dari:RequestBody,
                         @Part("sampai") sampai:RequestBody, @Part("perihal") perihal:RequestBody,
                         @Part("keterangan") keterangan:RequestBody,
                         @Part file: MultipartBody.Part):Call<PostIjinResponse>


}