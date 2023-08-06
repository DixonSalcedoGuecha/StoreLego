package com.example.storelego.ui.detailproduct.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storelego.model.DetailResponse
import com.example.storelego.model.ProductsResponse
import com.example.storelego.repository.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel  @Inject constructor(
    private val productsRepo: ProductsRepository
) : ViewModel() {

    private val _detailLiveData: MutableLiveData<DetailResponse> = MutableLiveData()
    val detailLiveData: LiveData<DetailResponse> = _detailLiveData

    fun getDetailProduct(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = productsRepo.getDetailProduct(id)
                _detailLiveData.postValue(response)
            } catch (e : Exception){
                Log.e("Exception", "getAllProducts: ${e.printStackTrace()} ", )
            }
        }
    }
}