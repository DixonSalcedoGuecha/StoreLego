package com.example.storelego.ui.shoppingcart.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storelego.model.ProductsResponse
import com.example.storelego.repository.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoppingCartViewModel @Inject constructor(
    private val productsRepo: ProductsRepository
): ViewModel() {
    private val _productsBdLiveData: MutableLiveData<ProductsResponse> = MutableLiveData()
    val productsBdLiveData: LiveData<ProductsResponse> = _productsBdLiveData


     fun getAllProducts() {

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = productsRepo.getAllProductsBd()
                println("Datos de la BD $response")
                _productsBdLiveData.postValue(response)
            } catch (e : Exception){
                Log.e("Exception", "getAllProducts: ${e.printStackTrace()} ", )
            }

        }
    }
}