package com.example.storelego.model

import com.example.storelego.model.entities.ProductEntity
import com.google.gson.annotations.SerializedName

data class Products(
    val id : Int,
    var name : String,
    @SerializedName("unit_price") val unitPrice: Int,
    val stock: Int,
    val image: String
)

fun ProductEntity.toProduct() = Products(id = idProduct, name = name, unitPrice = unitPrice, stock = stock, image = image)

