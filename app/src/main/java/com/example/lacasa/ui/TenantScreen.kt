package com.example.lacasa.ui

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class TenantScreen {
    data class Locataire(
        val id: Int = 0,
        val nom: String,
        val contact: String,
        val appartement: String,
        val dateEntree: String
    )

    class TenantViewModel : ViewModel() {
        private val _locataires = MutableStateFlow<List<Locataire>>(emptyList())
        val locataires: StateFlow<List<Locataire>> = _locataires.asStateFlow()

        fun ajouterLocataire(locataire: Locataire) {
            _locataires.update { currentList -> currentList + locataire.copy(id = currentList.size + 1) }
        }

        fun modifierLocataire(locataireModifie: Locataire) {
            _locataires.update { list ->
                list.map { if (it.id == locataireModifie.id) locataireModifie else it }
            }
        }

        fun supprimerLocataire(locataire: Locataire) {
            _locataires.update { list -> list.filter { it.id != locataire.id } }
        }
    }

    @Composable
    fun TenantScreenUI(
        navController: NavHostController,
        viewModel: TenantViewModel = remember { TenantViewModel() }
    ) {
        var isAdding by remember { mutableStateOf(false) }
        var isEditing by remember { mutableStateOf(false) }
        var locataireSelectionne by remember { mutableStateOf<Locataire?>(null) }
        var searchQuery by remember { mutableStateOf("") }

        val locataires by viewModel.locataires.collectAsState()
        val filteredLocataires = locataires.filter {
            it.nom.contains(searchQuery, ignoreCase = true) || it.appartement.contains(searchQuery, ignoreCase = true)
        }

        Scaffold(
            floatingActionButton = {
                FloatingActionButton(onClick = { isAdding = true }) {
                    Icon(Icons.Default.Add, contentDescription = "Ajouter un locataire")
                }
            }
        ) { paddingValues ->
            Column(modifier = Modifier.padding(paddingValues).fillMaxSize()) {

                // Barre de recherche
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    label = { Text("Rechercher un locataire") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )

                // Liste des locataires filtrés
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(filteredLocataires) { locataire ->
                        LocataireItem(
                            locataire = locataire,
                            onDelete = { viewModel.supprimerLocataire(locataire) },
                            onEdit = {
                                locataireSelectionne = locataire
                                isEditing = true
                            }
                        )
                    }
                }
            }
        }

        if (isAdding) {
            DialogForm("Ajouter un Locataire", onConfirm = {
                viewModel.ajouterLocataire(it)
                isAdding = false
            }, onDismiss = { isAdding = false })
        }

        if (isEditing && locataireSelectionne != null) {
            DialogForm("Modifier un Locataire", locataireSelectionne, onConfirm = {
                viewModel.modifierLocataire(it)
                isEditing = false
                locataireSelectionne = null
            }, onDismiss = {
                isEditing = false
                locataireSelectionne = null
            })
        }
    }

    @Composable
    fun LocataireItem(locataire: Locataire, onDelete: () -> Unit, onEdit: () -> Unit) {
        Card(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = locataire.nom, style = MaterialTheme.typography.bodyLarge)
                    Text(text = "Appartement: ${locataire.appartement}", style = MaterialTheme.typography.bodyMedium)
                    Text(text = "Date d'entrée: ${locataire.dateEntree}", style = MaterialTheme.typography.bodyMedium)
                }
                Row {
                    IconButton(onClick = onEdit) {
                        Icon(Icons.Default.Edit, contentDescription = "Modifier", tint = MaterialTheme.colorScheme.primary)
                    }
                    IconButton(onClick = onDelete) {
                        Icon(Icons.Default.Delete, contentDescription = "Supprimer", tint = MaterialTheme.colorScheme.error)
                    }
                }
            }
        }
    }

    @Composable
    fun DialogForm(title: String, locataire: Locataire? = null, onConfirm: (Locataire) -> Unit, onDismiss: () -> Unit) {
        var nom by remember { mutableStateOf(locataire?.nom ?: "") }
        var contact by remember { mutableStateOf(locataire?.contact ?: "") }
        var appartement by remember { mutableStateOf(locataire?.appartement ?: "") }
        var dateEntree by remember { mutableStateOf(locataire?.dateEntree ?: "") }

        val context = LocalContext.current
        val calendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            context,
            { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                calendar.set(year, month, dayOfMonth)
                dateEntree = sdf.format(calendar.time)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text(title) },
            text = {
                Column {
                    OutlinedTextField(value = nom, onValueChange = { nom = it }, label = { Text("Nom") })
                    OutlinedTextField(value = contact, onValueChange = { contact = it }, label = { Text("Contact") })
                    OutlinedTextField(value = appartement, onValueChange = { appartement = it }, label = { Text("Appartement") })
                    Button(onClick = { datePickerDialog.show() }, modifier = Modifier.padding(top = 8.dp)) {
                        Text(text = if (dateEntree.isNotEmpty()) "Date: $dateEntree" else "Sélectionner une date")
                    }
                }
            },
            confirmButton = {
                Button(onClick = {
                    onConfirm(
                        Locataire(
                            id = locataire?.id ?: 0,
                            nom = nom,
                            contact = contact,
                            appartement = appartement,
                            dateEntree = dateEntree
                        )
                    )
                }) {
                    Text("Enregistrer")
                }
            },
            dismissButton = {
                Button(onClick = onDismiss) { Text("Annuler") }
            }
        )
    }
}
