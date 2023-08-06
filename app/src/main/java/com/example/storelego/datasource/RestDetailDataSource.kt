package com.example.storelego.datasource

import com.example.storelego.model.DetailResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RestDetailDataSource {
     @GET("detail")
    suspend fun getDetailProduct( @Query("id") id : Int): DetailResponse

}