package com.example.storelego.ui.home.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storelego.model.Products
import com.example.storelego.model.ProductsResponse
import com.example.storelego.repository.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val productsRepo: ProductsRepository
) :ViewModel() {

    private val _productsLiveData: MutableLiveData<ProductsResponse> = MutableLiveData()
    val productsLiveData: LiveData<ProductsResponse> = _productsLiveData

    init {
        getAllProducts()
    }

    private fun getAllProducts() {

        viewModelScope.launch {
            try {
                val response = productsRepo.getAllProducts()
                println(response)
                _productsLiveData.postValue(response)
            } catch (e : Exception){
                Log.e("Exception", "getAllProducts: ${e.printStackTrace()} ", )
            }

        }
    }

    fun getInsertProduct(product: Products){
        viewModelScope.launch {
            try {
                productsRepo.getInsertProduct(product)

            } catch (e:Exception) {
                Log.e("Insert Produt BD", "getInsertProduct: ${e.printStackTrace()}", )
            }
        }
    }
}