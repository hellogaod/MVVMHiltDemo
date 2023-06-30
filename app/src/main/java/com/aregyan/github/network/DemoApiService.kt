package com.aregyan.github.network

import androidx.lifecycle.LiveData
import com.aregyan.github.network.model.BaseResponse
import com.aregyan.github.network.model.NetworkDemoEntity
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface DemoApiService {

    @GET("action/apiv2/banner?catalog=1")
   suspend fun demoGet(): LiveData<BaseResponse<NetworkDemoEntity?>?>?

    @FormUrlEncoded
    @POST("action/apiv2/banner")
    suspend  fun demoPost(@Field("catalog") catalog: String?): LiveData<BaseResponse<NetworkDemoEntity?>?>?
}