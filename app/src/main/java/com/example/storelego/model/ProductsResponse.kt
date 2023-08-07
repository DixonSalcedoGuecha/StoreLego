package com.example.storelego.model

data class ProductsResponse(
    val products: MutableList<Products> = mutableListOf()
)
