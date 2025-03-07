package com.example.lacasa.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.lacasa.ui.LoginScreen
import com.example.lacasa.ui.TenantScreen
import com.example.lacasa.ui.homeScreen
import com.example.lacasa.ui.mainScreen
import com.example.lacasa.ui.signUpScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navigation() {
    val navController = rememberNavController()
    val tenantViewModel: TenantScreen.TenantViewModel = viewModel() // Création du ViewModel

    NavHost(navController = navController, startDestination = "tenantScreen") {
        composable("homeScreen") {
            homeScreen(navController)
        }
        composable("loginScreen") {
            LoginScreen(navController)
        }
        composable("signUpScreen") {
            signUpScreen(navController)
        }
        composable("mainScreen") {
            mainScreen(navController)
        }
        composable("tenantScreen") {
            TenantScreen().TenantScreenUI(navController, tenantViewModel) // Passage correct du ViewModel
        }
    }
}
