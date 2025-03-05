package com.example.lacasa.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun signUpScreen() {
    // États pour les champs de formulaire
    var prenom by remember { mutableStateOf("") }
    var nom by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var motDePasse by remember { mutableStateOf("") }
    var confirmerMotDePasse by remember { mutableStateOf("") }

    // États pour les erreurs de validation
    var prenomError by remember { mutableStateOf(false) }
    var nomError by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf(false) }
    var motDePasseError by remember { mutableStateOf(false) }
    var confirmerMotDePasseError by remember { mutableStateOf(false) }

    // Fonction de validation
    fun validateForm(): Boolean {
        prenomError = prenom.isBlank()
        nomError = nom.isBlank()
        emailError =
            email.isBlank() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        motDePasseError = motDePasse.isBlank() || motDePasse.length < 6
        confirmerMotDePasseError = confirmerMotDePasse != motDePasse

        return !(prenomError || nomError || emailError || motDePasseError || confirmerMotDePasseError)
    }

    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {

        // UI
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Titre
            Text(
                text = "Inscription",
                style = TextStyle(fontSize = 28.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // Champ Prénom
            OutlinedTextField(
                value = prenom,
                onValueChange = { prenom = it },
                label = { Text("Prénom") },
                isError = prenomError,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp), // Définit des coins arrondis de 10.dp
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )
            if (prenomError) {
                Text(
                    text = "Le prénom est requis",
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 12.sp,
                    modifier = Modifier.align(Alignment.Start)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Champ Nom
            OutlinedTextField(
                value = nom,
                onValueChange = { nom = it },
                label = { Text("Nom") },
                isError = nomError,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp), // Définit des coins arrondis de 10.dp
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )
            if (nomError) {
                Text(
                    text = "Le nom est requis",
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 12.sp,
                    modifier = Modifier.align(Alignment.Start)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Champ Adresse mail
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Adresse mail") },
                isError = emailError,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp), // Définit des coins arrondis de 10.dp
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )
            if (emailError) {
                Text(
                    text = if (email.isBlank()) "L'adresse mail est requise" else "Adresse mail invalide",
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 12.sp,
                    modifier = Modifier.align(Alignment.Start)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Champ Mot de passe
            OutlinedTextField(
                value = motDePasse,
                onValueChange = { motDePasse = it },
                label = { Text("Mot de passe") },
                isError = motDePasseError,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp), // Définit des coins arrondis de 10.dp
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
            if (motDePasseError) {
                Text(
                    text = if (motDePasse.isBlank()) "Le mot de passe est requis" else "Le mot de passe doit contenir au moins 6 caractères",
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 12.sp,
                    modifier = Modifier.align(Alignment.Start)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Champ Confirmer mot de passe
            OutlinedTextField(
                value = confirmerMotDePasse,
                onValueChange = { confirmerMotDePasse = it },
                label = { Text("Confirmer mot de passe") },
                isError = confirmerMotDePasseError,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp), // Définit des coins arrondis de 10.dp
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
            if (confirmerMotDePasseError) {
                Text(
                    text = "Les mots de passe ne correspondent pas",
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 12.sp,
                    modifier = Modifier.align(Alignment.Start)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Bouton d'inscription
            Button(
                onClick = {
                    if (validateForm()) {
                        // Logique d'inscription (à implémenter)
                        println("Inscription réussie !")
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("S'inscrire")
            }
        }
    }

}