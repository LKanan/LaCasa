package com.example.lacasa.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.lacasa.R


@Composable
fun buildingScreen() {
    val navController = rememberNavController()
    val menuList = listOf("home","locataires", "imobiliers", "paiements")
    var menuSelected by rememberSaveable {
        mutableStateOf(menuList[0])
    }
    val menuIcons = mapOf(
        "home" to R.drawable.home, // Remplacez par vos drawables
        "locataires" to R.drawable.locataires, // Remplacez par vos drawables
        "imobiliers" to R.drawable.imobiliers,
        "paiements" to R.drawable.paiements
    )
    val buildings = mapOf(
        "building1" to R.drawable.building1, // Remplacez par vos drawables
        "building2" to R.drawable.building2,
        "building3" to R.drawable.building3,
        "building4" to R.drawable.building4
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
                            .size(40.dp) // Taille réduite
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
                                    .size(28.dp)
                            )
                        },
                    )
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {}
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Ajouter",
                )
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

            Card(
                modifier = Modifier
                    .fillMaxWidth(),
//                .padding(4.dp),
//            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                shape = RoundedCornerShape(0.dp)
            ) {
                Row(
                    modifier = Modifier
                        .padding(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Image(
                        painterResource(id = R.drawable.building1),
                        contentDescription = "Building1",
                        modifier = Modifier.size(70.dp)
                    )
//                Image(
//                    painterResource(id = R.drawable.imobiliers),
//                    ),
////                    contentDescription = stringResource(country.countryName),
////                    modifier = Modifier
////                        .size(80.dp) // Taille réduite
////                        .clip(RoundedCornerShape(8.dp)),
//                    contentScale = ContentScale.Crop
//                )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(
                            text = "Building1",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Start
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Nombre locataires : 10",
                            fontSize = 18.sp,
                            textAlign = TextAlign.Center
                        )
                        // Bouton pour afficher plus d'infos
//                    Row(modifier = Modifier.fillMaxWidth(),
//                        horizontalArrangement = Arrangement.End) {
//                        IconButton(
//                            onClick = { isExpanded = !isExpanded },
////                            modifier = Modifier.align(Alignment.End) // Aligne l'icône à la fin du conteneur
//                        ) {
//                            Icon(
//                                imageVector = if (isExpanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
//                                contentDescription = "Voir plus"
//                            )
//                        } }
//
//                    // Texte déroulant
//                    AnimatedVisibility(visible = isExpanded) {
//                        Text(
//                            text = stringResource(country.countryDescription),
////                            text = "",
//                            fontSize = 14.sp,
//                            modifier = Modifier.padding(top = 8.dp),
//                        )
//                    }
                    }
                }
            }
//                }
//            }
        }
    }

}
//    }