package com.example.storelego.ui.shoppingcart.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.storelego.model.ProductsResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ShoppingCartViewModel @Inject constructor(): ViewModel() {
    private val _productsBdLiveData: MutableLiveData<ProductsResponse> = MutableLiveData()
    val productsBdLiveData: LiveData<ProductsResponse> = _productsBdLiveData
}