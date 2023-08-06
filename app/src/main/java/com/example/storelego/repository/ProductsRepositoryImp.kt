package com.example.storelego.repository

import com.example.storelego.datasource.RestDetailDataSource
import com.example.storelego.datasource.RestProductDataSource
import com.example.storelego.model.DetailResponse
import com.example.storelego.model.ProductsResponse
import javax.inject.Inject


class ProductsRepositoryImp  @Inject constructor(
    private val productsDataSource: RestProductDataSource,
    private val detailDataSource: RestDetailDataSource
) : ProductsRepository{
    override suspend fun getAllProducts(): ProductsResponse {
        return productsDataSource.getAllProductsList()
    }

    override suspend fun getDetailProduct(id: Int): DetailResponse {
        return detailDataSource.getDetailProduct(id)
    }

}