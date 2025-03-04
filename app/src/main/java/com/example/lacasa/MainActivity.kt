package com.example.lacasa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.ui.text.TextStyle


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.example.compose.LaCasaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LaCasaTheme {
                inscriptionView()

            }
        }
    }
}

@Composable
fun LaCasaApp() {
    centralView()
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

@Composable
//fun homeView(){
//    fun homeView(onLoginClicked: (String, String) -> Unit, onCreateAccountClicked: () -> Unit) {
fun connexionView() {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
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
//                onClick = { onCreateAccountClicked() },
                onClick = {},
                style = TextStyle(
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 14.sp
                )
            )
        }
    }
}

@Composable
fun inscriptionView() {
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
                fontSize = 24.sp,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // Champ Prénom
            OutlinedTextField(
                value = prenom,
                onValueChange = { prenom = it },
                label = { Text("Prénom") },
                isError = prenomError,
                modifier = Modifier.fillMaxWidth(),
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
            buildingView()
//                }
//            }
        }
    }
}

@Composable
fun buildingView() {
//        Column(
////            modifier = Modifier.padding(1.dp),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
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

}
//    }


@Preview(showBackground = true)
@Composable
fun LaCasaAppPreview() {
    LaCasaTheme {
        inscriptionView()
    }
}