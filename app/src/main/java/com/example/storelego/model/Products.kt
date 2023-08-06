package com.example.storelego.model

import com.google.gson.annotations.SerializedName

data class Products(
    val id : Int,
    val name : String,
    @SerializedName("unit_price") val unitPrice: Int,
    val stock: Int,
    val image: String
)
