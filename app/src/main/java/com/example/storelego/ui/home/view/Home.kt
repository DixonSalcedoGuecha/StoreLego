package com.example.storelego.ui.home.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.storelego.model.Products
import com.example.storelego.model.ProductsResponse
import com.example.storelego.ui.home.viewmodel.HomeViewModel
import com.example.storelego.ui.navigation.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(homeViewModel: HomeViewModel, navigate: NavController){
    val productsState: ProductsResponse? by homeViewModel.productsLiveData.observeAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Lista de productos") },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(imageVector = Icons.Outlined.AccountCircle, contentDescription = "Cerrar Sesion")
                    }
                    IconButton(onClick = {  }) {
                        Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = "Carrito de compras")
                    }
                }
            )
        }, content = { innerPadding ->

            ProductContent(innerPadding,productsState, navigate )

        }
    )
}

@Composable
fun ProductContent(
    innerPadding: PaddingValues,
    productsState: ProductsResponse?,
    navigate: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        productsState?.let { productsResponse ->
            LazyColumn {
                items(productsResponse.products) { product ->
                    ProductItem(product = product, navigate = navigate)
                }
            }
        }
    }
}


@Composable
fun ProductItem(product: Products, navigate: NavController) {
    Row(

        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable{ navigate.navigate("${Routes.DetailScreen.route}/${product.id}")},
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