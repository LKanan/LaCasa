package com.example.lacasa.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.lacasa.R
import com.example.lacasa.data.Building
import com.example.lacasa.data.BuildingRepository
import com.example.lacasa.viewModel.BuildingViewModel
import com.example.lacasa.viewModel.BuildingViewModelFactory
import com.example.todolist.model.LaCasaDataBase

@Composable
fun BuildingGeneralItem(building: Building, onToggle: () -> Unit, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
//        Checkbox(checked = task.isDone, onCheckedChange = { onToggle() })
        Column(modifier = Modifier
            .weight(1f)
            .padding(start = 8.dp)
            .align(Alignment.CenterVertically)
            .clickable { onClick() }) {

            Text(text = building.name, style = MaterialTheme.typography.headlineSmall)
            Text(
                text = "Appartement(s): ${building.qtyApart}",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.secondary
            )
            Text(
                text = "Locataire(s): ${building.qtyApart}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.tertiary
            )
        }
    }
}


//Ecran principal pour la visualisation des batiments et leurs informations generales
//@RequiresApi(Build.VERSION_CODES.O)
//@OptIn(ExperimentalMaterial3Api::class)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun mainScreen(navController: NavController) {
    val context = LocalContext.current
    val database = remember { LaCasaDataBase.getDatabase(context) }
    val repository = remember { BuildingRepository(database.buildingDao()) }
    val viewModel: BuildingViewModel = viewModel(factory = BuildingViewModelFactory(repository))
    var showDialog by remember { mutableStateOf(false) } // État pour afficher la boîte de dialogue
    val buildings by viewModel.buildings.collectAsState(initial = emptyList())
//    val buildings: List<Building> by viewModel.buildings.collectAsState(initial = emptyList())


    val menuList = listOf("main", "building", "tenant", "payment")
    var menuSelected by rememberSaveable {
        mutableStateOf(menuList[0])
    }
    val menuIcons = mapOf(
        "main" to R.drawable.home, // Remplacez par vos drawables
        "building" to R.drawable.imobiliers,
        "tenant" to R.drawable.locataires, // Remplacez par vos drawables
        "payment" to R.drawable.paiements
    )
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "La Casa",
                        style = TextStyle(
                            fontWeight = MaterialTheme.typography.headlineMedium.fontWeight,
                            fontSize = MaterialTheme.typography.headlineMedium.fontSize
                        )
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
                            navController.navigate(menu+"Screen");
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
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .wrapContentSize()
                .padding(paddingValues)
        ) {
            LazyColumn {
                items(buildings) { building ->
                    BuildingGeneralItem(building,
                        onToggle = {},
//                        onClick = {navController.navigate("buildingGeneralDetails/${building.id}")
                        onClick = {navController.navigate("buildingGeneralDetails")
                        }
                    )
                }
            }
        }
    }
}