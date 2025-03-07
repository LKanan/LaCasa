package com.example.lacasa.ui

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.lacasa.R

//Ecran principal pour la visualisation des batiments et leurs informations generales
@Composable
fun mainScreen(navController: NavController) {
    val menuList = listOf("locataires", "imobiliers", "paiements")
    var menuSelected by rememberSaveable {
        mutableStateOf(menuList[1])
    }
    val menuIcons = mapOf(
        "locataires" to R.drawable.locataires, // Remplacez par vos drawables
        "imobiliers" to R.drawable.imobiliers,
        "paiements" to R.drawable.paiements
    )
    val buildings = mapOf(
        "building1" to R.drawable.building1, // Remplacez par vos drawables
        "building2" to R.drawable.building2,
        "building3" to R.drawable.building3,
        "building4" to R.drawable.building4,
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
        bottomBar = {
            NavigationBar {
                menuList.forEach { menu ->
                    NavigationBarItem(
                        selected = menuSelected == menu,
                        onClick = {
                            menuSelected = menu
                        },
                        icon = {
                            Icon(
                                painter = painterResource(
                                    id = menuIcons[menu] ?: R.drawable.imobiliers
                                ),
                                contentDescription = menu,
                                modifier = Modifier
                                    .size(40.dp)
                            )
                        },
                    )
                }
            }
        }
    ) {
//        it ->
            paddindValues ->
        Box(
            modifier = Modifier
                .padding(paddindValues)
                .fillMaxSize()
                .background(color = Color.White),
//            contentAlignment = Alignment.Center
        ) {

//            LazyColumn(modifier = Modifier, contentPadding = it) {
//                items(buildings) { building ->
            buildingScreen()
//                }
//            }
        }
    }
}