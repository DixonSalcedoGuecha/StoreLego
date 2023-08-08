package com.example.storelego.repository

import com.example.storelego.datasource.RestBuyDataSource
import com.example.storelego.datasource.RestDetailDataSource
import com.example.storelego.datasource.RestProductDataSource
import com.example.storelego.model.DetailResponse
import com.example.storelego.model.Products
import com.example.storelego.model.ProductsResponse
import com.example.storelego.model.dao.ProductsDao
import com.example.storelego.model.entities.toProductEntity
import com.example.storelego.model.toProduct
import javax.inject.Inject


class ProductsRepositoryImp  @Inject constructor(
    private val productsDataSource: RestProductDataSource,
    private val detailDataSource: RestDetailDataSource,
    private val restBuyDataSource: RestBuyDataSource,
    private val productsDao: ProductsDao
) : ProductsRepository{
    override suspend fun getAllProducts(): ProductsResponse {
        return productsDataSource.getAllProductsList()
    }

    override suspend fun getDetailProduct(id: Int): DetailResponse {
        return detailDataSource.getDetailProduct(id)
    }

    override suspend fun getInsertProduct(product: Products) {
        productsDao.insertProduct(product.toProductEntity())
    }

    override suspend fun getAllProductsBd(): ProductsResponse {
        var productsList : ProductsResponse = ProductsResponse()
        productsDao.getAllProductsBD().map { productEntity ->
            productsList.products.add (  productEntity.toProduct() )
        }

        return productsList

    }

    override suspend fun setBuyProducts(products: ProductsResponse) {
        restBuyDataSource.setBuyProducts(products)
    }

}