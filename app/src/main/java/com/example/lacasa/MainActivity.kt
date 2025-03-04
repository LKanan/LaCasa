package com.example.lacasa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.compose.LaCasaTheme
import com.example.lacasa.ui.mainScreen
import com.example.lacasa.ui.navigation.Navigation
import com.example.lacasa.ui.signUpScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContent {
            LaCasaTheme {
                Navigation()
            }
        }
    }
}

@Composable
fun LaCasaApp(navController: NavController) {
    mainScreen(navController)
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LaCasaAppPreviewDark() {
    LaCasaTheme(darkTheme = true) {
        Navigation()
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LaCasaAppPreviewWhite() {
    LaCasaTheme {
        Navigation()
    }
}