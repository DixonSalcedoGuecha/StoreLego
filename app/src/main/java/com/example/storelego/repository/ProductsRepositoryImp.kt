package com.example.storelego.repository

import com.example.storelego.datasource.RestDataSource
import com.example.storelego.model.Products
import com.example.storelego.model.ProductsResponse
import javax.inject.Inject


class ProductsRepositoryImp  @Inject constructor(
    private val dataSource: RestDataSource
) : ProductsRepository{
    override suspend fun getAllProducts(): ProductsResponse {
        return dataSource.getAllProductsList()
    }

}