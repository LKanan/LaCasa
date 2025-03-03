package com.example.lacasa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.currentComposer
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


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LaCasaTheme {
                homeView()
            }
        }
    }
}

@Composable
fun LaCasaApp() {
    centralView()
}

@Composable
fun centralView() {
    val menuList = listOf("locataires", "imobiliers", "paiements")
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

@Composable
//fun homeView(){
//    fun homeView(onLoginClicked: (String, String) -> Unit, onCreateAccountClicked: () -> Unit) {
fun connexionView() {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

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

        // Champ pour l'adresse e-mail
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Adresse e-mail") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            shape = RoundedCornerShape(10.dp), // Définit des coins arrondis de 16.dp
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
            onClick = { },
            modifier = Modifier
//                .fillMaxWidth()
                .size(200.dp, 60.dp)
                .padding(bottom = 16.dp)
        ) {
            Text(text="Se connecter",
                style = TextStyle(fontSize = 18.sp))
        }

        // Option pour créer un compte
        ClickableText(
            text = AnnotatedString("Vous n'avez pas de compte ? Créez-en un ici"),
//                onClick = { onCreateAccountClicked() },
            onClick = {},
            style = TextStyle(
                color = Color.Blue,
                fontSize = 14.sp
            )
        )
    }
}

@Composable
fun homeView() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier
                .size(140.dp)
                .padding(bottom = 16.dp)
        )
        Text(
            text = "Bienvenue sur La Casa",
            style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}


@Preview(showBackground = true)
@Composable
fun LaCasaAppPreview() {
    homeView()
}