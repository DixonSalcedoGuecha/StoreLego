package com.example.storelego.datasource

import com.example.storelego.model.ProductsResponse
import retrofit2.http.GET

interface RestProductDataSource {
    @GET("allProducts")
    suspend fun getAllProductsList(): ProductsResponse

}