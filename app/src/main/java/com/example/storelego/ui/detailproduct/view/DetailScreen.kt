package com.example.storelego.ui.detailproduct.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.storelego.R
import com.example.storelego.model.DetailResponse
import com.example.storelego.model.Products
import com.example.storelego.model.toProduct
import com.example.storelego.ui.detailproduct.viewmodel.DetailViewModel
import com.example.storelego.ui.navigation.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(detailViewModel: DetailViewModel, id: Int, navigate: NavHostController) {
    Scaffold(
        modifier = Modifier.padding(20.dp),
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navigate.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Volver atrás"
                        )
                    }
                },
                title = { Text(text = "Lista de productos") },

                actions = {

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
            Detail(detailViewModel, id, innerPadding)

        })
}

@Composable
fun Detail(detailViewModel: DetailViewModel, productId: Int?, innerPadding: PaddingValues) {

    val detailState: DetailResponse? by detailViewModel.detailLiveData.observeAsState()

    if (productId != null) {
        detailViewModel.getDetailProduct(productId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(1) {
                Text(
                    text = detailState?.name.toString(),
                    style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)
                )
                Spacer(modifier = Modifier.padding(20.dp))
                HeaderDetailImage(
                    detailState = detailState ?: DetailResponse(
                        id = 0,
                        name = "",
                        unitPrice = 0,
                        stock = 0,
                        image = "",
                        description = ""
                    ),
                    detailViewModel = detailViewModel
                )
                Spacer(modifier = Modifier.padding(30.dp))
                Text(text = detailState?.description.toString())
                Spacer(modifier = Modifier.padding(20.dp))
                Text(
                    text = "Price: ${detailState?.unitPrice.toString()}",
                    modifier = Modifier.align(Alignment.Start)
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Stock: ${detailState?.stock.toString()}",
                    modifier = Modifier.align(Alignment.Start)
                )
            }
        }
    }
}

@Composable
fun HeaderDetailImage(detailState: DetailResponse, detailViewModel: DetailViewModel) {

    var isCartIconVisible by remember { mutableStateOf(true) }

    AsyncImage(
        model = detailState.image,
        contentDescription = "Image Product",
        modifier = Modifier
            .width(300.dp)
            .height(300.dp)
    )
    val cartIcon = if (isCartIconVisible) {
        painterResource(R.drawable.ic_add_product)
    } else {
        painterResource(R.drawable.ic_check_product)

    }
    if (detailState.stock > 0) {

        Image(
            painter = cartIcon,
            contentDescription = "Carrito de compras",
            modifier = Modifier
                .fillMaxWidth()
                .width(30.dp)
                .height(30.dp)
                .clickable {
                    if (isCartIconVisible) {
                        detailViewModel.getInsertProduct(
                            detailState.toProduct()
                        )
                        isCartIconVisible = !isCartIconVisible
                    }
                }, alignment = Alignment.BottomEnd
        )
    }
}


