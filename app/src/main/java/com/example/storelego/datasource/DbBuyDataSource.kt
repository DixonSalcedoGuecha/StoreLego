package com.example.storelego.datasource


import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.storelego.model.Products
import com.example.storelego.model.dao.ProductsDao
import com.example.storelego.model.entities.ProductEntity

@Database(entities = [ProductEntity::class], version = 1 )
abstract class DbBuyDataSource : RoomDatabase() {
    abstract fun productsDao(): ProductsDao
}