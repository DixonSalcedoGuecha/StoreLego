package com.example.storelego.ui.detailproduct.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.storelego.model.DetailResponse
import com.example.storelego.ui.detailproduct.viewmodel.DetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(detailViewModel: DetailViewModel, id: Int, navigate: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navigate.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Volver atrás")
                    }
                },
                title = { Text(text = "Lista de productos") },

                actions = {

                    IconButton(onClick = {  }) {
                        Icon(
                            imageVector = Icons.Outlined.AccountCircle,
                            contentDescription = "Volver atras"
                        )
                    }
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = "Carrito de compras"
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

        LazyColumn {
            items(1) {
                Text(
                    text = detailState?.name.toString(),
                    style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)
                )
                Spacer(modifier = Modifier.padding(20.dp))
                HeaderDetailImage(detailState?.image.toString())
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
fun HeaderDetailImage(image: String) {
    AsyncImage(
        model = image,
        contentDescription = "Image Product",
        modifier = Modifier
            .width(300.dp)
            .height(300.dp)
    )
}