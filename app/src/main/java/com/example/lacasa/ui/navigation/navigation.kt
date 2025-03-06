package com.example.lacasa.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.lacasa.ui.LoginScreen
import com.example.lacasa.ui.homeScreen
import com.example.lacasa.ui.mainScreen
import com.example.lacasa.ui.signUpScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "homeScreen") {
        composable("homeScreen") {
            homeScreen(navController)
        }
        composable("loginScreen"){
            LoginScreen(navController)
        }
        composable("signUpScreen"){
            signUpScreen(navController)
        }
        composable("mainScreen"){
            mainScreen(navController)
        }
//        composable("task_detail/{taskId}"){
//                backStackEntry ->
//            val taskId = backStackEntry.arguments?.getString("taskId")?.toIntOrNull()
//            taskId?.let{TaskDetailScreen(navController, taskId)}
//        }
    }
}
