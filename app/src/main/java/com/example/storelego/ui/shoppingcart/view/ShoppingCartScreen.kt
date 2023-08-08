package com.example.storelego.ui.shoppingcart.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
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

    shoppingCartViewModel.getAllProducts()
    val productsBdState: ProductsResponse? by shoppingCartViewModel.productsBdLiveData.observeAsState()

    Scaffold(
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
                title = { Text(text = "Carrito de compras") },

                )
        }, content = { innerPadding ->

            ShoppingCartContent(innerPadding, productsBdState, navigate, shoppingCartViewModel)

        }
    )
}

@Composable
fun ShoppingCartContent(
    innerPadding: PaddingValues,
    productsBdState: ProductsResponse?,
    navigate: NavController,
    shoppingCartViewModel: ShoppingCartViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        productsBdState?.let { productsResponse ->
            LazyColumn(modifier = Modifier.weight(0.90f)) {
                items(productsResponse.products) { product ->
                    ShoppingProductItem(product = product, navigate = navigate, shoppingCartViewModel = shoppingCartViewModel)
                }
            }
        }

        Spacer(modifier = Modifier.weight(0.10f))
        DataShopping(productsBdState?.products ?: mutableListOf())
        Spacer(modifier = Modifier.height(16.dp))
        ButtonBuyProducts(shoppingCartViewModel, productsBdState ?: ProductsResponse())
    }
}

@Composable
fun DataShopping(products: MutableList<Products>) {
    var total: Int = 0
    for (i in 0 until products.size) {
        total += products[i].unitPrice
    }
    Text(
        text = "Total Compra = $ $total pesos",
        style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold),
        modifier = Modifier.padding(20.dp)
    )
}

@Composable
fun ButtonBuyProducts(shoppingCartViewModel: ShoppingCartViewModel, products: ProductsResponse) {
    val productsBuy: Boolean? by shoppingCartViewModel.productsBuyState.observeAsState()
    Box(modifier = Modifier.padding(30.dp, 0.dp, 30.dp, 0.dp)) {
        Button(
            onClick = { shoppingCartViewModel.setBuyProduct(products) },
            shape = RoundedCornerShape(50.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp), enabled = products.products.size > 0
        ) {
            Text(text = "Comprar productos")
        }
        CustomDialog(showDialog = productsBuy ?: false)
    }
}

@Composable
fun CustomDialog(
    showDialog: Boolean
) {
    if (showDialog) {
        Dialog(
            onDismissRequest = {  }
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_confirmation_buy),
                    contentDescription = "Confirmation of buy",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(150.dp)
                        .padding(bottom = 16.dp)
                )

                Text(
                    text = "Gracias por tu compra",
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                }
            }
        }
    }
}

@Composable
fun ShoppingProductItem(
    product: Products,
    navigate: NavController,
    shoppingCartViewModel: ShoppingCartViewModel
) {
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
                .height(30.dp).clickable {shoppingCartViewModel.setDeleteProduct(product.id) },
            alignment = Alignment.BottomEnd
        )

    }
}
