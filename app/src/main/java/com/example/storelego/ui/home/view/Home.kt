package com.example.storelego.ui.home.view

import androidx.activity.OnBackPressedCallback
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.storelego.model.Products
import com.example.storelego.model.ProductsResponse
import com.example.storelego.ui.home.viewmodel.HomeViewModel
import com.example.storelego.ui.navigation.Routes
import com.example.storelego.R
import com.example.storelego.model.toProduct
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(homeViewModel: HomeViewModel, navigate: NavController) {
    val productsState: ProductsResponse? by homeViewModel.productsLiveData.observeAsState()
    val auth: FirebaseAuth = Firebase.auth

    DisposableEffect(Unit) {
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                navigate.navigate(Routes.HomeScreen.route)
            }
        }
        onDispose {
            onBackPressedCallback.remove()
        }
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Lista de productos") },
                actions = {
                    IconButton(onClick = {
                        auth.signOut()
                        navigate.navigate(Routes.LoginScreen.route)
                    }) {

                        Image(
                            painter = painterResource(R.drawable.ic_exit_session),
                            contentDescription = "Cerrar Sesion",
                            modifier = Modifier
                                .width(30.dp)
                                .height(30.dp)
                        )
                    }
                    IconButton(onClick = { navigate.navigate(Routes.ShoppingCartScreen.route) }) {
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

            ProductContent(innerPadding, productsState, navigate, homeViewModel)

        }
    )
}

@Composable
fun ProductContent(
    innerPadding: PaddingValues,
    productsState: ProductsResponse?,
    navigate: NavController,
    homeViewModel: HomeViewModel
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
                    ProductItem(
                        product = product,
                        navigate = navigate,
                        homeViewModel = homeViewModel
                    )
                }
            }
        }
    }
}


@Composable
fun ProductItem(product: Products, navigate: NavController, homeViewModel: HomeViewModel) {
    var isCartIconVisible by remember { mutableStateOf(true) }
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

        val cartIcon = if (isCartIconVisible) {
            painterResource(R.drawable.ic_add_product)
        } else {
            painterResource(R.drawable.ic_check_product)
        }

        Image(
            painter = cartIcon,
            contentDescription = "Carrito de compras",
            modifier = Modifier
                .fillMaxWidth()
                .width(30.dp)
                .height(30.dp)
                .clickable {
                    if (isCartIconVisible) {
                        homeViewModel.getInsertProduct(product)
                        isCartIconVisible = !isCartIconVisible
                    }
                }, alignment = Alignment.BottomEnd
        )


    }


}