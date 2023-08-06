package com.example.storelego.ui.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storelego.model.Products
import com.example.storelego.model.ProductsResponse
import com.example.storelego.repository.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val productsRepo: ProductsRepository
) :ViewModel() {

   /* private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var listProducts: ProductsResponse = ProductsResponse(products = emptyList())

    fun getAllProducts(): ProductsResponse {

        viewModelScope.launch(Dispatchers.IO) {

            listProducts = productsRepo.getAllProducts()

        }


        return listProducts
    }*/

    private val _productsLiveData: MutableLiveData<ProductsResponse> = MutableLiveData()
    val productsLiveData: LiveData<ProductsResponse> = _productsLiveData

    init {
        getAllProducts()
    }

    private fun getAllProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = productsRepo.getAllProducts()
            _productsLiveData.postValue(response)
        }
    }
}