package com.projectdevs.donacionesapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
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
import com.projectdevs.donacionesapp.ui.theme.Gray100

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForumChatScreen(
    navigateBack:() -> Unit = {}
) {
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
                        Text(text = "Ana", fontSize = 18.sp)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navigateBack()
                    }) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowLeft,
                            contentDescription = null
                        )
                    }

                },

                )
        },
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
                    text = "Iniciaste este chat. Ver perfil del donatario",
                    fontSize = 13.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                )
            }

            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.End) {
                SpeechBubbleRight("Hola! vi tu publicación y soy de la zona. Tengo lo que buscas. Estoy libre a las 17")
                Spacer(Modifier.height(5.dp))
                SpeechBubbleRight("Te parece bien reunirnos enfrente de Coppel?")

            }
            Spacer(Modifier.height(20.dp))
            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {
                SpeechBubbleLeft("Buenas tardes, perfecto! muchas gracias. Sisi, te espero en Coppel")
                Spacer(Modifier.height(5.dp))
            }
            Spacer(Modifier.height(20.dp))

            Spacer(Modifier.height(20.dp))

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
            Column(
                modifier = Modifier
                    .background(Gray100)
                    .fillMaxWidth()
                    .padding(20.dp)
                    .clip(RoundedCornerShape(5.dp)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Vimos que paso la fecha acordada ¿entregaste el pedido?",
                    fontSize = 13.sp,
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.height(10.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {

                    Button(onClick = {}, shape = RoundedCornerShape(5.dp), colors = ButtonDefaults.buttonColors( containerColor = Color.DarkGray)) {
                        Text(
                            text = "Si, fue entregado.", fontSize = 13.sp
                        )
                    }

                    Button(onClick = {}, shape = RoundedCornerShape(5.dp),  colors = ButtonDefaults.buttonColors( containerColor = Color.Gray)) {
                        Text(
                            text = "No fue entregado.",
                            fontSize = 13.sp
                        )
                    }
                }
            }



            Spacer(Modifier.height(10.dp))

        }
    }
}