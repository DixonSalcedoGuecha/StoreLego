package com.example.storelego.ui.detailproduct.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.storelego.R
import com.example.storelego.ui.detailproduct.viewmodel.DetailViewModel
import com.example.storelego.ui.login.view.EmailField
import com.example.storelego.ui.login.view.ForgotPassword
import com.example.storelego.ui.login.view.HeaderImage
import com.example.storelego.ui.login.view.Login
import com.example.storelego.ui.login.view.LoginButton
import com.example.storelego.ui.login.view.PasswordField
import com.example.storelego.ui.login.view.SocialMediaButton
import com.example.storelego.ui.login.view.UserRegister

@Composable
fun DetailScreen(detailViewModel: DetailViewModel) {
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        Detail(detailViewModel)

    }
}

@Composable
fun Detail( viewModel: DetailViewModel) {

   /* val email : String by  viewModel.email.observeAsState(initial = "")
    val password : String by  viewModel.password.observeAsState(initial = "")
    val loginEnable : Boolean by  viewModel.loginEnable.observeAsState(initial = false)*/
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {


        HeaderImage()
        Spacer(modifier = Modifier.padding(30.dp))
        Text(text = "Detalle")
        Spacer(modifier = Modifier.padding(20.dp))
        Text(text = "Precio")
        Spacer(modifier = Modifier.height(40.dp))
        Text(text = "Valor")


    }
}
