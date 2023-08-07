package com.example.storelego.model.entities

import com.example.storelego.model.Products
import com.example.storelego.model.ProductsResponse
import com.example.storelego.model.toProduct

data class ListProductEntity(

    val productEntity: List<ProductEntity> = emptyList()
)

fun ListProductEntity.toProductsResponse() = ProductsResponse(products = productEntity.map { it.toProduct() })

