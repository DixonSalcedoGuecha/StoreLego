package com.example.storelego.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.storelego.model.Products

@Entity(tableName = "products")
data class ProductEntity (
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = "idProduct") var idProduct: Int,
    @ColumnInfo(name = "name")var name : String,
    @ColumnInfo(name = "unitPrice")  var unitPrice: Int,
    @ColumnInfo(name = "stock") var stock: Int,
    @ColumnInfo(name = "image") var image: String

)
fun Products.toProductEntity() = ProductEntity(idProduct = id, name = name, unitPrice = unitPrice, stock = stock, image = image)

