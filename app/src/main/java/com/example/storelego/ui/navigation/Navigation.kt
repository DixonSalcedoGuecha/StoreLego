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
import com.example.storelego.ui.login.ui.LoginScreen
import com.example.storelego.ui.login.ui.LoginViewModel
import com.example.storelego.ui.login.ui.SingUpScreen
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Navigation(navController: NavHostController = rememberNavController(),
               loginViewModel: LoginViewModel = hiltViewModel(),
               homeViewModel: HomeViewModel = hiltViewModel()
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
                Home(homeViewModel = homeViewModel)
            }
        }

    }
}