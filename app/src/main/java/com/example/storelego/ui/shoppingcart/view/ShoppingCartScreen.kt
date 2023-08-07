package com.example.storelego.ui.shoppingcart.view

import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.storelego.R
import com.example.storelego.model.Products
import com.example.storelego.model.ProductsResponse
import com.example.storelego.ui.navigation.Routes
import com.example.storelego.ui.shoppingcart.viewmodel.ShoppingCartViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingCartScreen(shoppingCartViewModel: ShoppingCartViewModel, navigate: NavController) {
    val productsBdState: ProductsResponse? by shoppingCartViewModel.productsBdLiveData.observeAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navigate.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Volver atrás")
                    }
                },
                title = { Text(text = "Carrito de compras") },
                actions = {

                    IconButton(onClick = { }) {
                        Image(
                            painter = painterResource(R.drawable.ic_shoppin_cart_purple),
                            contentDescription = "Carrito de compras",
                            modifier = Modifier
                                .width(30.dp)
                                .height(30.dp)
                        )
                    }
                }
            )
        }, content = { innerPadding ->

            ShoppingCartContent(innerPadding, productsBdState, navigate)

        }
    )
}

@Composable
fun ShoppingCartContent(innerPadding: PaddingValues, productsBdState: ProductsResponse?, navigate: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        productsBdState?.let { productsResponse ->
            LazyColumn {
                items(productsResponse.products) { product ->
                    ShoppingProductItem(product = product, navigate = navigate)
                }
            }
        }
    }
}

@Composable
fun ShoppingProductItem(product: Products, navigate: NavController) {
    Row(

        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End,

        ) {
        AsyncImage(
            model = product.image,
            contentDescription = "Image Product",
            modifier = Modifier
                .width(60.dp)
                .height(60.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier
            .clickable { navigate.navigate("${Routes.DetailScreen.route}/${product.id}") }) {

            Text(text = "Nombre: ${product.name}")
            Text(text = "Precio: ${product.unitPrice}")
            Text(text = "Stock: ${product.stock}")
        }

        Image(
            painter = painterResource(R.drawable.ic_remove_product),
            contentDescription = "Carrito de compras",
            modifier = Modifier
                .fillMaxWidth()
                .width(30.dp)
                .height(30.dp), alignment = Alignment.BottomEnd
        )

    }
}
