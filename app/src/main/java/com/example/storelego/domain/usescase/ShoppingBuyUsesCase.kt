package com.example.storelego.domain.usescase

import com.example.storelego.model.dao.ProductsDao
import javax.inject.Inject


class ShoppingBuyUsesCase @Inject constructor(private val productsDao: ProductsDao) {

    suspend fun setShoppingBuyUsesCase() {
        productsDao.deleteForBuy()
    }

    suspend fun setDeleteProduct(id: Int) {
        productsDao.deleteProduct(id)
    }
}