package com.example.lacasa.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
import java.time.LocalDate

@Composable
fun BuildingItem(building: Building, onToggle: () -> Unit, onClick: () -> Unit) {
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

            Text(text = building.name, style = MaterialTheme.typography.bodyLarge)
            Text(
                text = "Début: ${building.qtyApart}",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun buildingScreen(navController: NavController) {
    val context = LocalContext.current
    val database = remember { LaCasaDataBase.getDatabase(context) }
    val repository = remember { BuildingRepository(database.buildingDao()) }
    val viewModel: BuildingViewModel = viewModel(factory = BuildingViewModelFactory(repository))
    var showDialog by remember { mutableStateOf(false) } // État pour afficher la boîte de dialogue
    val buildings by viewModel.buildings.collectAsState(initial = emptyList())
//    val buildings: List<Building> by viewModel.buildings.collectAsState(initial = emptyList())


    val menuList = listOf("main", "building", "tenant", "payment")
    var menuSelected by rememberSaveable {
        mutableStateOf(menuList[1])
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
                        text = "Immeubles",
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
        floatingActionButton = {
            FloatingActionButton(
                onClick = {  showDialog = true }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Ajouter",
                )
            }
        }
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
                        onClick = { navController.navigate("buildingGeneralDetailsScreen/${building.id}") }
                    )
                }
            }
        }
        if (showDialog) {
            AddBuildingDialog(
                context = context,
                onDismiss = { showDialog = false },
                onConfirm = { name, address, type, qtyApart ->
                    if (name.isNotBlank() ) {
                        viewModel.addBuilding(name, address, type,qtyApart, apartImagePath = "", creationDate = LocalDate.now())
                    }
                    showDialog = false
                }
            )
        }
    }
}