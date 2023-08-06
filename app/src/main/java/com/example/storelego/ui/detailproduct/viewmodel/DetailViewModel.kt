package com.example.storelego.ui.detailproduct.viewmodel

import androidx.lifecycle.ViewModel
import com.example.storelego.repository.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel  @Inject constructor(
    private val productsRepo: ProductsRepository
) : ViewModel() {

}