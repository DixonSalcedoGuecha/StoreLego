package com.example.storelego.repository

import com.example.storelego.model.DetailResponse
import com.example.storelego.model.Products
import com.example.storelego.model.ProductsResponse

interface ProductsRepository {
    suspend fun getAllProducts(): ProductsResponse
    suspend fun getDetailProduct(id: Int): DetailResponse
    suspend fun getInsertProduct(product: Products)
    suspend fun getAllProductsBd(): ProductsResponse
}