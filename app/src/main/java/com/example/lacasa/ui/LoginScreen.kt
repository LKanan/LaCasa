package com.example.lacasa.ui

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.lacasa.R
import com.example.lacasa.data.UserRepository
import com.example.lacasa.viewModel.UserViewModel
import com.example.lacasa.viewModel.UserViewModelFactory
import com.example.todolist.model.LaCasaDataBase

@Composable
//fun homeView(){
//    fun homeView(onLoginClicked: (String, String) -> Unit, onCreateAccountClicked: () -> Unit) {
fun LoginScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val context = LocalContext.current
    val database = remember { LaCasaDataBase.getDatabase(context) }
    val repository = remember { UserRepository(database.userDao()) }
    val viewModel: UserViewModel = viewModel(factory = UserViewModelFactory(repository))

    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Titre
            Text(
                text = "Connexion",
                style = TextStyle(fontSize = 28.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(bottom = 24.dp)
            )

            if (errorMessage != null) {
                Text(
                    text = errorMessage!!, // Affiche le message d'erreur
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 12.sp,
                    modifier = Modifier.align(Alignment.Start)
                )
            }
            // Champ pour l'adresse e-mail
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Adresse e-mail") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                shape = RoundedCornerShape(10.dp), // Définit des coins arrondis de 10.dp
                singleLine = true
            )

            // Champ pour le mot de passe
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Mot de passe") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            painter = painterResource(
                                id = if (passwordVisible) R.drawable.eye_off else R.drawable.eye
                            ),
                            contentDescription = if (passwordVisible) "Cacher le mot de passe" else "Afficher le mot de passe"
                        )
                    }
                },
                shape = RoundedCornerShape(10.dp),
                singleLine = true
            )

            // Bouton de connexion
            Button(
//                onClick = { onLoginClicked(email, password) },
                onClick = {
                    if (email.isBlank() || password.isBlank()) {
                        errorMessage = "Veuillez remplir tous les champs."
                    } else {
                        // Authentifier l'utilisateur
                        viewModel.authenticateUser(email, password) { success, message ->
                            if (success) {
                                errorMessage = null
                                navController.navigate("mainScreen")
                                println("hey")
                            } else {
                                errorMessage = message
                                println("hello")
                                Log.d("MyButton", "Le bouton a été appuyé !")
                            }
                        }
                    }
                },
                modifier = Modifier
//                .fillMaxWidth()
                    .size(200.dp, 60.dp)
                    .padding(bottom = 16.dp),
//            colors =  ButtonDefaults.buttonColors(
//                containerColor = MaterialTheme.colorScheme.primaryContainer
//            )
            ) {
                Text(
                    text = "Se connecter",
                    style = TextStyle(fontSize = 18.sp),
                )
            }

            // Option pour créer un compte
            ClickableText(
                text = AnnotatedString("Vous n'avez pas de compte ? Créez-en un ici"),
                onClick = {navController.navigate("signUpScreen")},
                style = TextStyle(
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 14.sp
                )
            )
        }
    }
}