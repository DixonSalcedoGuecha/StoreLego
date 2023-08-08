package com.example.storelego.model.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.storelego.model.ProductsResponse
import com.example.storelego.model.entities.ProductEntity

@Dao
interface ProductsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(productEntity: ProductEntity)

    @Query("SELECT * FROM products_table ORDER BY id DESC")
   suspend fun getAllProductsBD(): List<ProductEntity>

    @Query("DELETE FROM products_table WHERE idProduct = :id")
    suspend fun deleteProduct(id: Int) : Int

    @Query("DELETE FROM products_table ")
    suspend fun deleteForBuy()


}