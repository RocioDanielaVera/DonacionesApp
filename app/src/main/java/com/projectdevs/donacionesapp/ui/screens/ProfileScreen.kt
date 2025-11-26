package com.projectdevs.donacionesapp.ui.screens

import androidx.compose.foundation.BorderStroke
import com.projectdevs.donacionesapp.R
import androidx.compose.material.icons.Icons
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Checkroom
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LaptopChromebook
import androidx.compose.material.icons.filled.LocalDining
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.SentimentVerySatisfied
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.projectdevs.donacionesapp.domain.historialDonaciones
import com.projectdevs.donacionesapp.ui.theme.DonacionesAppTheme
import com.projectdevs.donacionesapp.ui.theme.Green30

@OptIn(ExperimentalMaterial3Api::class)
@Composable
    fun ProfileScreen(navController: NavController,
                      onEditClick: () -> Unit,
                      onBackClick: () -> Unit,
                      onLogoutClick: () -> Unit,
//                      onAddClick: () -> Unit,
                      onDonationCardClick: (String) -> Unit,
                      modifier: Modifier = Modifier) {
        Scaffold(
            modifier = modifier.fillMaxSize(),
            containerColor = Color(0xFFF5F8F6),
            topBar = {
                TopAppBar(
                    title = { Text("Perfil") },
                    actions = {
                        IconButton(onClick = onEditClick) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Editar",
                                tint = Color(0xFF2E7D32)
                            )
                        }
                        IconButton(onClick = onLogoutClick) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                                contentDescription = "Salir",
                                tint = Color(0xFFe0150c)
                            )
                        }
                    }
            )
        },
//            floatingActionButton = {
//                ExtendedFloatingActionButton(
//                    onClick = onAddClick,
//                    icon = { Icon(imageVector = Icons.Filled.Add, contentDescription = null) },
//                    text = { Text("Nueva publicación") },
//                    containerColor = MaterialTheme.colorScheme.primary,
//                    contentColor = Color.Black
//                )
//            },
    ) { innerPadding ->
            ProfileContent(
                modifier = Modifier.padding(
                    top = innerPadding.calculateTopPadding(),
                    start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
                    end = innerPadding.calculateEndPadding(LocalLayoutDirection.current)
                ),
                onEditClick = onEditClick,
                onDonationCardClick = { category ->
                    navController.navigate("donation_history/${category}")
                },
                bottomPadding = innerPadding.calculateBottomPadding()
            )
    }
}
@Composable
fun ProfileContent(
    modifier: Modifier = Modifier,
    onEditClick: () -> Unit,
    bottomPadding: Dp,
    onDonationCardClick: (String) -> Unit
) {

    val history = historialDonaciones

    val misOpiniones = listOf(
        Opinion(
            nombre = "Pedro Diaz",
            fecha = "20/11/2025",
            calificacion = 4,
            descripcion = "Conforme con la entrega. Excelente comunicación."
        ),
        Opinion(
            nombre = "María López",
            fecha = "15/10/2025",
            calificacion = 5,
            descripcion = "Muy rápido y amable."
        ),
        Opinion(
            nombre = "Mario Paz",
            fecha = "11/10/2025",
            calificacion = 5,
            descripcion = "Excelente."
        )
    )

    val totalDonaciones = history.size

    val fixedCategories = listOf("Alimentos", "Indumentaria", "Electrodomésticos")

    val donationsSummary = history
        .groupBy { it.categoria }
        .map { (category, list) ->
            val count = list.size

            val lastDateItem = list.maxByOrNull { item ->
                formatForSort(item.fecha)
            }
            val lastDateString = lastDateItem?.fecha ?: "N/A"

            Triple(category, count, lastDateString)
        }

    val finalDonationsListWithDate = fixedCategories.map { fixedCategory ->
        val match = donationsSummary.firstOrNull { it.first == fixedCategory }

        match ?: Triple(fixedCategory, 0, "N/A")
    }.filter { it.second > 0 }


        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(top = 16.dp, start = 16.dp, end = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min)
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
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Juan López", fontWeight = FontWeight.Bold, fontSize = 25.sp)
                }
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("$totalDonaciones")
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
                                append("$totalDonaciones")
                            }
                            append(" Donaciones")
                        },
                        fontSize = 14.sp,
                        color = Color.Black
                    )
                }
                }
            Spacer(modifier = Modifier.height(15.dp))
            // ubicación
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
                    Text(
                        text = "San Justo",
                        fontSize = 14.sp
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Filled.SentimentVerySatisfied,
                        tint = MaterialTheme.colorScheme.primary,
                        contentDescription = "Estado de ánimo",
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Se unió en 2025",
                        fontSize = 14.sp
                    )
                }
            }

            Divider(modifier = Modifier.padding(vertical = 12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        "Mis opiniones",
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

            LazyRow(
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

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                "Mis donaciones",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
                contentPadding = PaddingValues(bottom = 100.dp)) {
                items(finalDonationsListWithDate) { (category, count, lastDate) ->

                    DonationCard(
                        category,
                        count,
                        lastDate = lastDate,
                        onClick = onDonationCardClick
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }

@Composable
fun DonationCard(category: String, count: Int, lastDate: String,onClick: (String) -> Unit) {
    val verdeFondo = colorResource(id = R.color.verdeFondo)
    val icon = when (category) {
        "Alimentos" -> Icons.Default.LocalDining
        "Indumentaria" -> Icons.Default.Checkroom
        "Electrodomésticos" -> Icons.Default.Category
        else -> Icons.Default.Category
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(category) },
       colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(width = 1.dp, color = Color.LightGray)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = category,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .size(40.dp)
                    .padding(end = 12.dp)
            )

            Column{
                Text(
                    text = category,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(modifier = Modifier
                    .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Text(
                        text = "Cantidad: $count",
                        color = Color.DarkGray,
                        fontSize = 13.sp
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Text(
                        text = "Última: $lastDate",
                        color = Color.Gray,
                        fontSize = 11.sp
                    )
                }

            }
        }
    }
}

@Composable
fun OpinionCard(nombre: String, fecha: String, calificacion: Int, descripcion: String) {
    val verdeFondo = colorResource(id = R.color.verdeFondo)

    Card(
        modifier = Modifier
            .width(200.dp)
            .height(100.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(width = 1.dp, color = Color.LightGray)
    ) {
        Column(
            modifier = Modifier.padding(10.dp)
                .fillMaxSize()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                painter = painterResource(id = R.drawable.foto_perfil),
                contentDescription = "Foto perfil",
                modifier = Modifier
                    .size(32.dp)
                    .padding(end = 8.dp)
                    .clip(CircleShape),

                contentScale = ContentScale.Crop
            )

                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Text(
                        text = nombre,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                    Text(
                        text = fecha,
                        color = Color.DarkGray,
                        fontSize = 9.sp
                    )
                }

            }

            Spacer(modifier = Modifier.height(4.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                repeat(calificacion) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = Color(0xFFFFC107),
                        modifier = Modifier.size(16.dp)
                    )
                }
                repeat(5 - calificacion) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = Color.Gray,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = descripcion,
                fontSize = 12.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

data class Opinion(
    val nombre: String,
    val fecha: String,
    val calificacion: Int,
    val descripcion: String
)

fun formatForSort(date: String): String {
    val parts = date.split("/")
    return if (parts.size == 3) "${parts[2]}/${parts[0]}/${parts[1]}" else date
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    DonacionesAppTheme {
        val navController = rememberNavController()
        ProfileScreen(
            navController = navController,
            onEditClick = {},
            onBackClick = {},
//            onAddClick = {},
            onLogoutClick = {},
            onDonationCardClick = {}
        )
    }
}
