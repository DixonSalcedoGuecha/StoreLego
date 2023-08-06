package com.example.storelego.ui.home.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.storelego.model.Products
import com.example.storelego.model.ProductsResponse
import com.example.storelego.ui.home.viewmodel.HomeViewModel

@Composable
fun Home(homeViewModel: HomeViewModel){
    val productsState: ProductsResponse? by homeViewModel.productsLiveData.observeAsState()

    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        Text("Lista de productos", fontSize = 30.sp, modifier = Modifier.padding(16.dp))

        productsState?.let { productsResponse ->
            LazyColumn {
                items(1) { product ->
                   productsResponse.products.map {  ProductItem(product = it) }
                }
            }
        }
    }
}

@Composable
fun ProductItem(product: Products) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = product.image, contentDescription = "Image Product", modifier = Modifier.width(60.dp).height(60.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            //Text(text = "ID: ${product.id}")
            Text(text = "Nombre: ${product.name}")
            Text(text = "Precio: ${product.unitPrice}")
            Text(text = "Stock: ${product.stock}")
        }
    }
}