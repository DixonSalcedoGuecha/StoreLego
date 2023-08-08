package com.example.storelego.datasource

import com.example.storelego.model.Products
import com.example.storelego.model.ProductsResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface RestBuyDataSource {
    @POST("buy")
    suspend fun setBuyProducts(@Body products: ProductsResponse)
}