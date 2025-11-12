package com.projectdevs.donacionesapp.ui.screens

import com.projectdevs.donacionesapp.R
import androidx.compose.material.icons.Icons
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.projectdevs.donacionesapp.ui.theme.DonacionesAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController,
                  onEditClick: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Perfil") },
                navigationIcon = {
                    IconButton(onClick = { /* volver atras */ }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* ir a crear donacion */ },
                containerColor = colorResource(id = R.color.verde),
                        contentColor = Color.White
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Agregar donación"
                )
            }
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = false,
                    onClick = { /* ir al home */ },
                    icon = { Icon(Icons.Default.Home, contentDescription = "Inicio") },
                    label = { Text("Inicio") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { /* ir a solicitar donacion */ },
                    icon = { Icon(Icons.Default.Favorite, contentDescription = "Solicitud donación") },
                    label = { Text("Solicitar") }
                )
                NavigationBarItem(
                    selected = true,
                    onClick = { /* ir a perfil */ },
                    icon = { Icon(Icons.Default.Person, contentDescription = "Perfil") },
                    label = { Text("Perfil") }
                )
            }
        }
    ) { innerPadding ->
        ProfileContent(Modifier.padding(innerPadding),
            onEditClick = onEditClick
        )
    }
}
@Composable
fun ProfileContent(
    modifier: Modifier = Modifier,
    onEditClick: () -> Unit
) {
    val verde = colorResource(id = R.color.verde)

    val donations = listOf(
        "Alimentos" to 5,
        "Indumentaria" to 2,
        "Electrodomésticos" to 1
    )
    val totalDonaciones = donations.sumOf { it.second }

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min)
            ) {
                Image(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Foto de perfil",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray)
                        .align(Alignment.Center)
                )

                Button(
                    onClick = onEditClick,
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = verde),
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 8.dp)
                ) {
                    Icon(
                        Icons.Default.Edit,
                        contentDescription = "Editar",
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Editar", fontSize = 14.sp)
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Rango", fontWeight = FontWeight.Bold, fontSize = 25.sp)
                    Spacer(modifier = Modifier.width(6.dp))
                    Icon(
                        Icons.Default.Star,
                        contentDescription = "Rango",
                        tint = Color(0xFFFFD700),
                        modifier = Modifier.size(25.dp)
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text("Donaciones: $totalDonaciones", fontSize = 14.sp, color = Color.Black)
            }
            Spacer(modifier = Modifier.height(15.dp))
            // Nombre y ubicación
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text("Nombre", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    Text("Usuario 1234", fontSize = 14.sp)
                }

                Column {
                    Text("Ubicación", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    Text("San Justo", fontSize = 14.sp)
                }
            }

            Divider(modifier = Modifier.padding(vertical = 12.dp))

            Text(
                "Mis donaciones",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Lista de donaciones
            LazyColumn {
                items(donations) { (category, count) ->
                    DonationCard(category, count)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

        }
    }

@Composable
fun DonationCard(category: String, count: Int) {
    val verdeFondo = colorResource(id = R.color.verdeFondo)
    val icon = when (category) {
        "Alimentos" -> Icons.Default.Info
        "Indumentaria" -> Icons.Default.Info
        "Electrodomésticos" -> Icons.Default.Info
        else -> Icons.Default.Info
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* detalle */ },
        colors = CardDefaults.cardColors(containerColor = verdeFondo),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(8.dp)
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

            Column {
                Text(
                    text = category,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Cantidad: $count",
                    color = Color.DarkGray,
                    fontSize = 13.sp
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    DonacionesAppTheme {
        val navController = rememberNavController()
        ProfileScreen(
            navController = navController,
            onEditClick = {}
        )
    }
}
