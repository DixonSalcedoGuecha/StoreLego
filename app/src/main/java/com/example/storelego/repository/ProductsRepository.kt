package com.example.storelego.repository

import com.example.storelego.model.DetailResponse
import com.example.storelego.model.ProductsResponse

interface ProductsRepository {
    suspend fun getAllProducts(): ProductsResponse
    suspend fun getDetailProduct(id: Int): DetailResponse
}