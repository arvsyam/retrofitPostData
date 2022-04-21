package com.adl.retrofitpost.service

import com.adl.retrofitpost.model.PostTrackingResponse
import com.adl.retrofitpost.model.SearchTrackingResponse
import retrofit2.Call
import retrofit2.http.*

interface ITracking {
    @FormUrlEncoded
    @Headers("X-Api-Key:FC35B899DC137F0CB6B56035FA37068A")
    @POST("api/tracking/add")
    fun addTrack(@Field("id_user") id_user :String,
                 @Field("latitude") latitude :String,
                 @Field("longitude") longitude :String,
                 @Field("timestamp") timestamp :String): Call<PostTrackingResponse>

    @Headers("X-Api-Key:FC35B899DC137F0CB6B56035FA37068A")
    @GET("api/tracking/all?")
    fun searchTracker(@Query("filter") query:String):Call<SearchTrackingResponse>
}