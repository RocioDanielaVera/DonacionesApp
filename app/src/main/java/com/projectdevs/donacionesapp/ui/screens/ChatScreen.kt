package com.projectdevs.donacionesapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.projectdevs.donacionesapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(navController: NavController) {

    var message by rememberSaveable { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.foto_perfil),
                            contentDescription = "Avatar",
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .border(1.dp, Color.White, CircleShape),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(Modifier.width(10.dp))
                        Text(text = "", fontSize = 18.sp)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowLeft,
                            contentDescription = null
                        )
                    }

                },

                )
        },
        bottomBar = {
            Column(
                modifier = Modifier.background(Color.White)
                    .fillMaxWidth()
                    .windowInsetsPadding(WindowInsets.navigationBars)
                    .padding(bottom = 16.dp, end =16.dp, start = 16.dp)
            ){
                Row(
                    horizontalArrangement = Arrangement.Absolute.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(20.dp),
                        value = message,
                        onValueChange = { message = it },
                        placeholder = { Text("Mensaje") },
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Default.Send,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    )

                }
            }
        }
    ) { paddingValues ->
        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Box(Modifier.padding(horizontal = 65.dp, vertical = 30.dp)) {
                Text(
                    text = "Iniciaste este chat. Ver perfil del donador",
                    fontSize = 13.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                )
            }

            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.End) {
                SpeechBubbleRight("Hola!Vi tu publicación y soy de la zona ¿sigue disponible?")

            }
            Spacer(Modifier.height(20.dp))
            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {
                SpeechBubbleLeft("Hola¿Cómo estás? sigue disponible. Hago entregas enfrente de central Oeste?")
                Spacer(Modifier.height(5.dp))
                SpeechBubbleLeft("Estoy libre hoy a las 17 o mañana a las 12:00 PM")
            }
            Spacer(Modifier.height(20.dp))
            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.End) {
                SpeechBubbleRight("Perfecto! Estoy libre mañana a las 12:00PM")
                Spacer(Modifier.height(5.dp))
                SpeechBubbleRight("Te reservo ese día")
            }
            Spacer(Modifier.height(20.dp))
            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {
                SpeechBubbleLeft("Dale, lo agendo. Nos vemos mañana!")
            }
            Spacer(Modifier.height(20.dp))
            Box(Modifier.padding(horizontal = 30.dp)) {
                Text(
                    text = "Si te vas a reunir con alguien en persona, cuéntales a familiares y amigos adónde vas. Usa la función de compartir la ubicación en tiempo real directamente con un amigo o familiar durante la reunión. Ver más consejos de seguridad",
                    fontSize = 13.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(Modifier.height(10.dp))
            MeetNotification()
            Spacer(Modifier.height(10.dp))
            Message()


            //
            Spacer(Modifier.height(50.dp))

        }
    }
}


@Composable
fun SpeechBubbleLeft(message: String) {
    Box(
        Modifier
            .width(350.dp)
            .clip(
                shape = RoundedCornerShape(topEnd = 20.dp, bottomEnd = 20.dp, bottomStart = 20.dp),
            )
            .background(Color(0xFFEEEEEE))
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(text = message)
        }
    }
}

@Composable
fun SpeechBubbleRight(message: String) {
    Box(
        Modifier
            .width(350.dp)
            .clip(
                shape = RoundedCornerShape(20.dp, bottomEnd = 20.dp, bottomStart = 20.dp),
            )
            .background(Color(0xFF74D581))
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(text = message)
        }
    }
}

@Preview
@Composable
fun MeetNotification() {
    Box(
        Modifier
            .fillMaxWidth()
            .height(130.dp)
            .clip(
                shape = RoundedCornerShape(20.dp),
            )
            .background(Color(0xFFEEEEEE))
    ) {
        Column(modifier = Modifier.padding(15.dp)) {
            Spacer(Modifier.height(5.dp))
            Text(text = "Juan agendo un horario de encuentro.")

            Spacer(Modifier.height(10.dp))

            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = null,
                    modifier = Modifier.size(15.dp)
                )
                Spacer(Modifier.width(15.dp))
                Text(text = "12:00 PM - 13:00 PM - Gregorio de Laferrere", color = Color.DarkGray)
            }

            Spacer(Modifier.height(10.dp))

            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Default.LocationOn,
                    contentDescription = null,
                    modifier = Modifier.size(15.dp)
                )
                Spacer(Modifier.width(15.dp))
                Text(text = "Central Oeste - Comodoro Py 3102, B1757")
            }

        }
    }
}

@Preview
@Composable
fun Message() {
    Box(
        Modifier
            .fillMaxWidth()
            .height(160.dp)
            .clip(
                shape = RoundedCornerShape(20.dp),
            )
            .background(Color(0xFFEEEEEE))
    ) {
        Column(modifier = Modifier.padding(15.dp)) {
            Spacer(Modifier.height(5.dp))
            Text(text = "Juan marco el articulo como entregado.")
            Text(text = "¿Cómo fue tu experiencia?", color = Color.Gray, fontSize = 13.sp)
            Spacer(Modifier.height(10.dp))
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp),
                onClick = {
//                    navigateToScreen()
                },
                shape = RoundedCornerShape(10.dp),
                colors =
                    ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFDCDCDC),
                    ),
            ) {
                Text(text = "Calificar a Juan", color = Color.DarkGray)
            }
        }

    }
}