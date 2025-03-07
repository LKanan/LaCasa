package com.example.lacasa.ui

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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class TenantScreen {
    // Classe représentant un locataire avec ses informations de base
    data class Locataire(
        val id: Int = 0,
        val nom: String,
        val contact: String,
        val appartement: String,
        val dateEntree: String
    )

    // ViewModel pour la gestion des locataires (ajout, modification, suppression, recherche)
    class TenantViewModel : ViewModel() {
        private val _locataires = MutableStateFlow<List<Locataire>>(emptyList())
        val locataires: StateFlow<List<Locataire>> = _locataires.asStateFlow()

        // Ajoute un nouveau locataire à la liste
        fun ajouterLocataire(locataire: Locataire) {
            _locataires.update { currentList -> currentList + locataire.copy(id = currentList.size + 1) }
        }

        // Modifie un locataire existant en remplaçant les anciennes valeurs par les nouvelles
        fun modifierLocataire(locataireModifie: Locataire) {
            _locataires.update { list ->
                list.map { if (it.id == locataireModifie.id) locataireModifie else it }
            }
        }

        // Supprime un locataire de la liste
        fun supprimerLocataire(locataire: Locataire) {
            _locataires.update { list -> list.filter { it.id != locataire.id } }
        }

        // Recherche des locataires selon le nom ou l'appartement
        fun rechercherLocataires(query: String): StateFlow<List<Locataire>> {
            return locataires.map { list ->
                list.filter {
                    it.nom.contains(query, ignoreCase = true) || it.appartement.contains(query, ignoreCase = true)
                }
            }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
        }
    }

    // Interface utilisateur principale de l'écran des locataires
    @Composable
    fun TenantScreenUI(navController: NavController? = null, viewModel: TenantViewModel = remember { TenantViewModel() }) {
        var searchQuery by remember { mutableStateOf("") }
        val locataires by viewModel.rechercherLocataires(searchQuery).collectAsState()
        var locataireSelectionne by remember { mutableStateOf<Locataire?>(null) }
        var isAdding by remember { mutableStateOf(false) }
        var isEditing by remember { mutableStateOf(false) }

        Scaffold(
            floatingActionButton = {
                FloatingActionButton(onClick = { isAdding = true }) {
                    Icon(Icons.Default.Add, contentDescription = "Ajouter un locataire")
                }
            }
        ) { paddingValues ->
            Column(modifier = Modifier.padding(paddingValues)) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    label = { Text("Rechercher un locataire") },
                    modifier = Modifier.fillMaxWidth().padding(16.dp)
                )

                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(locataires) { locataire ->
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

        // Affichage du formulaire d'ajout de locataire
        if (isAdding) {
            DialogForm("Ajouter un Locataire", onConfirm = {
                viewModel.ajouterLocataire(it)
                isAdding = false
            }, onDismiss = { isAdding = false })
        }

        // Affichage du formulaire de modification de locataire existant
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

    // Composable affichant un locataire sous forme de carte avec options de modification et suppression
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

    // Formulaire d'ajout et de modification de locataire sous forme de dialogue
    @Composable
    fun DialogForm(title: String, locataire: Locataire? = null, onConfirm: (Locataire) -> Unit, onDismiss: () -> Unit) {
        var nom by remember { mutableStateOf(locataire?.nom ?: "") }
        var contact by remember { mutableStateOf(locataire?.contact ?: "") }
        var appartement by remember { mutableStateOf(locataire?.appartement ?: "") }
        var dateEntree by remember { mutableStateOf(locataire?.dateEntree ?: "") }

        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text(title) },
            text = {
                Column {
                    OutlinedTextField(value = nom, onValueChange = { nom = it }, label = { Text("Nom") })
                    OutlinedTextField(value = contact, onValueChange = { contact = it }, label = { Text("Contact") })
                    OutlinedTextField(value = appartement, onValueChange = { appartement = it }, label = { Text("Appartement") })
                    OutlinedTextField(value = dateEntree, onValueChange = { dateEntree = it }, label = { Text("Date d'entrée") })
                }
            },
            confirmButton = {
                Button(onClick = {
                    onConfirm(Locataire(id = locataire?.id ?: 0, nom = nom, contact = contact, appartement = appartement, dateEntree = dateEntree))
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
