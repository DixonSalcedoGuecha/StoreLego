package com.example.storelego.ui.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.storelego.ui.screens.Screen
import com.example.storelego.ui.home.view.Home
import com.example.storelego.ui.home.viewmodel.HomeViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.storelego.ui.detailproduct.view.DetailScreen
import com.example.storelego.ui.detailproduct.viewmodel.DetailViewModel
import com.example.storelego.ui.login.view.LoginScreen
import com.example.storelego.ui.login.viewmodel.LoginViewModel
import com.example.storelego.ui.login.view.SingUpScreen
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Navigation(navController: NavHostController = rememberNavController(),
               loginViewModel: LoginViewModel = hiltViewModel(),
               homeViewModel: HomeViewModel = hiltViewModel(),
               detailViewModel: DetailViewModel = hiltViewModel()
){
    Screen {
        NavHost(navController = navController, startDestination = if(FirebaseAuth.getInstance().currentUser?.email.isNullOrEmpty()) { Routes.LoginScreen.route} else { Routes.HomeScreen.route} ) {
            composable(Routes.LoginScreen.route) {
                LoginScreen(
                    viewModel = loginViewModel,
                    navigate = navController

                )
            }
            composable(Routes.SingUpScreen.route){
                SingUpScreen(
                    viewModel = loginViewModel,
                    navigate = navController
                )
            }

            composable(Routes.HomeScreen.route) {
                Home(homeViewModel = homeViewModel, navigate = navController)
            }

            composable(Routes.DetailScreen.route + "/{id}", arguments = listOf(navArgument(name = "id"){
                type = NavType.IntType
            })) {
                DetailScreen(detailViewModel = detailViewModel,it.arguments!!.getInt("id"),  navigate =  navController)
            }
        }

    }
}