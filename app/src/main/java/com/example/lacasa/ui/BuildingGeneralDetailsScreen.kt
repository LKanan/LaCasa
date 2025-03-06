package com.example.lacasa.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.lacasa.viewModel.BuildingViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun buildingGeneralDetailsScreen(navController: NavController, buildingId:Int=1, viewModel: BuildingViewModel= viewModel()){

    var expanded by remember { mutableStateOf(false) } // État du menu déroulant
    val options = listOf("Option 1", "Option 2", "Option 3") // Liste des options
    var selectedOption by remember { mutableStateOf(options[0]) } // Option sélectionnée

    Scaffold(topBar = { TopAppBar(title={Text(text="Informations générales")})}) {
            padding ->
        Column(modifier=Modifier.fillMaxSize().padding(padding).padding(16.dp)) {
            Text("Vous avez sélectionné l'immeuble : ${buildingId}", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(16.dp))

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = selectedOption,
                    onValueChange = {},
                    readOnly = true,
                    modifier = Modifier.menuAnchor(), // Nécessaire pour aligner le menu
                    label = { Text("Sélectionner une option") },
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Filled.ArrowDropDown,
                            contentDescription = "Dropdown",
                            modifier = Modifier.clickable { expanded = !expanded }
                        )
                    }
                )

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.exposedDropdownSize()
                ) {
                    options.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option) },
                            onClick = {
                                selectedOption = option
                                expanded = false
                            }
                        )
                    }
                }
            }


            Button(onClick = {navController.popBackStack()}) {
                Text("Retour")
            }
        }
    }
}