package com.projectdevs.donacionesapp.ui.screens

import com.projectdevs.donacionesapp.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.outlined.Chat
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.SentimentVerySatisfied
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.projectdevs.donacionesapp.domain.Donation
import com.projectdevs.donacionesapp.domain.donaciones
import com.projectdevs.donacionesapp.ui.components.DonationCard
import com.projectdevs.donacionesapp.ui.theme.DonacionesAppTheme
import com.projectdevs.donacionesapp.ui.theme.Green30

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DonatorProfileScreen(navController: NavController,
                         donorId: Int,
                         navigateBack: () -> Unit,
                         donaciones: List<Donation>,
                  modifier: Modifier = Modifier) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = Color(0xFFF5F8F6),
        topBar = {
            TopAppBar(
                title = { Text("Perfil") },
                navigationIcon = {
                    IconButton(onClick = {
                        navigateBack()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                            contentDescription = "Volver"
                        )
                    }

                },
                actions = {
                    IconButton(onClick = {/* TODO Abrir mensajes */ }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.Chat,
                            contentDescription = "Mensajes",
                            tint = Color(0xFF2E7D32),
                            modifier = Modifier.size(28.dp)
                        )
                    }
                },
            )
        },
    ) { innerPadding ->
        DonatorProfileContent(
            modifier = Modifier.padding(
                top = innerPadding.calculateTopPadding(),
                start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
                end = innerPadding.calculateEndPadding(LocalLayoutDirection.current)
            ),
            bottomPadding = innerPadding.calculateBottomPadding(),
            donorId = donorId,
            donaciones = donaciones
        )
    }
}
@Composable
fun DonatorProfileContent(
    modifier: Modifier = Modifier,
    bottomPadding: Dp,
    donorId: Int,
    donaciones: List<Donation>
) {
    val donatorProfile = getDonatorProfileInfo(donorId)
    val donorActiveDonations = donaciones.filter { it.donorId == donorId }

    val misOpiniones = listOf(
        Opinion(
            nombre = "Mario Ludueña",
            fecha = "20/11/2025",
            calificacion = 4,
            descripcion = "Conforme con la entrega. Excelente comunicación."
        ),
        Opinion(
            nombre = "Ramón Lista",
            fecha = "15/10/2025",
            calificacion = 5,
            descripcion = "Muy rápido y amable."
        ),
        Opinion(
            nombre = "Ángel Lamadrid",
            fecha = "11/10/2025",
            calificacion = 5,
            descripcion = "Excelente."
        )
    )
    //contenedor principal
    LazyColumn(
        modifier = modifier
            .fillMaxSize(),
            //.padding(horizontal = 0.dp),
        contentPadding = PaddingValues(bottom = bottomPadding + 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Min)
                        .padding(top = 16.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.foto_perfil),
                        contentDescription = "Foto de perfil",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                            .background(Color.LightGray)
                            .border(
                                width = 3.dp,
                                color = MaterialTheme.colorScheme.primary,
                                shape = CircleShape
                            )
                            .align(Alignment.Center)
                    )

                    Image(
                        painter = painterResource(id = R.drawable.medalla_oro),
                        contentDescription = "Medalla de oro",
                        modifier = Modifier
                            .size(50.dp)
                            .align(Alignment.BottomCenter)
                            .offset(x = 30.dp, y = 15.dp)
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(donatorProfile.name, fontWeight = FontWeight.Bold, fontSize = 25.sp)
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            buildAnnotatedString {
                                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                    append("${donorActiveDonations.size}")
                                }
                                append(" Publicaciones")
                            },
                            fontSize = 14.sp,
                            color = Color.Black
                        )
                        Text(
                            buildAnnotatedString {
                                append(" - ")
                                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                    append("15")
                                }
                                append(" Donaciones")
                            },
                            fontSize = 14.sp,
                            color = Color.Black
                        )
                    }
                }
                Spacer(modifier = Modifier.height(15.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(0.8f),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Filled.LocationOn,
                            tint = MaterialTheme.colorScheme.primary,
                            contentDescription = "Ubicación",
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = donatorProfile.location, fontSize = 14.sp)
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Filled.SentimentVerySatisfied,
                            tint = MaterialTheme.colorScheme.primary,
                            contentDescription = "Estado de ánimo",
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "Se unió en ${donatorProfile.joinYear}", fontSize = 14.sp)
                    }
                }

                Divider(modifier = Modifier.padding(vertical = 12.dp))

               Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            "Opiniones",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                        )
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Ver todas",
                            color = Green30,
                            fontSize = 12.sp,
                            textDecoration = TextDecoration.Underline
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
        item {
            LazyRow(//contentPadding = PaddingValues(horizontal = 16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                items(misOpiniones) { opinion ->
                    OpinionCard(
                        nombre = opinion.nombre,
                        fecha = opinion.fecha,
                        calificacion = opinion.calificacion,
                        descripcion = opinion.descripcion
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Publicaciones activas",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
            )
            //Spacer(modifier = Modifier.height(8.dp))
        }

        items(
            items = donorActiveDonations.chunked(2),
            key = { row -> row.hashCode() }
        ) { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Box(modifier = Modifier.weight(1f).padding(4.dp)) {
                    DonationCard(rowItems.first()) { }
                }

                if (rowItems.size > 1) {
                    Box(modifier = Modifier.weight(1f).padding(4.dp)) {
                        DonationCard(rowItems.last()) { }
                    }
                } else {
                    Spacer(modifier = Modifier.weight(1f).padding(4.dp))
                }
            }
        }
    }
}

data class ProfileInfo(
    val name: String,
    val location: String,
    val score: Double,
    val joinYear: String
)

fun getDonatorProfileInfo(donorId: Int): ProfileInfo {
    val donorDonations = donaciones.filter { it.donorId == donorId }

    if (donorDonations.isEmpty()) {
        return ProfileInfo(
            name = "Donante Desconocido",
            location = "N/A",
            score = 0.0,
            joinYear = "N/A"
        )
    }

    val representativeDonation = donorDonations.first()

    return ProfileInfo(
        name = representativeDonation.donorName,
        location = representativeDonation.location,
        score = representativeDonation.donorScore,
        joinYear = "2025",
    )
}

@Preview(showBackground = true)
@Composable
fun DonatorProfileScreenPreview() {
    DonacionesAppTheme {
        val navController = rememberNavController()
        DonatorProfileScreen(
            navController = navController,
            donorId = 1,
            donaciones = donaciones,
            navigateBack = {}
        )
    }
}
