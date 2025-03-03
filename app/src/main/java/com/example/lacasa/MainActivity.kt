package com.example.lacasa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lacasa.ui.theme.LaCasaTheme
import androidx.compose.ui.text.TextStyle

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LaCasaTheme {
                LaCasaApp()
            }
        }
    }
}

@Composable
fun LaCasaApp() {
    val menuList= listOf("locataires", "imobiliers","paiements")
    var menuSelected by rememberSaveable {
        mutableStateOf(menuList[1])
    }
    val menuIcons = mapOf(
        "locataires" to R.drawable.locataires, // Remplacez par vos drawables
        "imobiliers" to R.drawable.imobiliers,
        "paiements" to R.drawable.paiements
    )
    @OptIn(ExperimentalMaterial3Api::class)
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "La Casa",
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    )
                },
                navigationIcon = {
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = "Menu",
                        modifier = Modifier
                            .size(50.dp) // Taille réduite
                            .clip(RoundedCornerShape(50.dp)),
                    )
                },

//                colors = TopAppBarDefaults.topAppBarColors(
//                    containerColor = Color.Blue, // Changez cette couleur selon vos besoins
//                ),
//                modifier = Modifier
////                    .background(color = Color.Gr)
            )
        },
        bottomBar={
            NavigationBar {
                menuList.forEach{ menu ->
                    NavigationBarItem(
                        selected = menuSelected == menu,
                        onClick = {
                            menuSelected = menu
                        },
                        icon = {
                            Icon(
                                painter = painterResource(id = menuIcons[menu] ?: R.drawable.imobiliers),
                                contentDescription = "Home"
                            )
                        },
                    )
                }
            }
        }
    ) { paddindValues ->
        Box(
            modifier = Modifier
                .padding(paddindValues)
                .fillMaxSize()
                .background(color = Color.White),
            contentAlignment = Alignment.Center
        ) {
            Text("Hello, World!")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LaCasaAppPreview() {
    LaCasaApp()
}