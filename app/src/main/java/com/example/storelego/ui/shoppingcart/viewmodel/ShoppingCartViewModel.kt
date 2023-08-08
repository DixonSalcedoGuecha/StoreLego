package com.example.storelego.ui.shoppingcart.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storelego.domain.usescase.ShoppingBuyUsesCase
import com.example.storelego.model.ProductsResponse
import com.example.storelego.repository.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoppingCartViewModel @Inject constructor(
    private val productsRepo: ProductsRepository,
    private val productsUsesCase: ShoppingBuyUsesCase
): ViewModel() {
    private val _productsBdLiveData: MutableLiveData<ProductsResponse> = MutableLiveData()
    val productsBdLiveData: LiveData<ProductsResponse> = _productsBdLiveData

    private val _productsBuyState: MutableLiveData<Boolean> = MutableLiveData()
    var productsBuyState: LiveData<Boolean> = _productsBuyState



     fun getAllProducts() {

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = productsRepo.getAllProductsBd()
                _productsBdLiveData.postValue(response)
            } catch (e : Exception){
                Log.e("Exception", "getAllProducts: ${e.printStackTrace()} ", )
            }

        }
    }

    fun setBuyProduct(products: ProductsResponse) {

        viewModelScope.launch() {
            try {
                productsRepo.setBuyProducts(products)
                productsUsesCase.setShoppingBuyUsesCase()
                _productsBuyState.postValue(true)
                delay(4000)
                getAllProducts()
                _productsBuyState.postValue(false)
            } catch (e : Exception) {
                _productsBuyState.postValue(false)
                Log.e("Exception", "getAllProducts: ${e.printStackTrace()} ", )
            }

        }
    }

    fun setDeleteProduct(idProduct: Int){
        viewModelScope.launch {
            try {
                productsUsesCase.setDeleteProduct(idProduct)
                getAllProducts()
            } catch (e:Exception) {
                Log.e("Delete Produt BD", "getDelete: ${e.printStackTrace()}", )
            }
        }
    }
}