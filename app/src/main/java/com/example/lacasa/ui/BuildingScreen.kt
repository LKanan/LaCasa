package com.example.lacasa.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lacasa.R




@Composable
fun buildingScreen() {
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