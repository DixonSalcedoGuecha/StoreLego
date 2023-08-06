package com.example.storelego.ui.navigation

sealed class Routes (val route : String) {

    object LoginScreen : Routes("LoginScreen")

    object HomeScreen : Routes("HomeScreen")

    object SingUpScreen : Routes("SingUpScreen")

}