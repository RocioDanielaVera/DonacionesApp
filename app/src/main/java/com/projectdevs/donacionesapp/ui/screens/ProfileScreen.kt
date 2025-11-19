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
import com.projectdevs.donacionesapp.domain.historialDonaciones
import com.projectdevs.donacionesapp.ui.theme.DonacionesAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
    fun ProfileScreen(navController: NavController,
                      onEditClick: () -> Unit,
                      onBackClick: () -> Unit,
                      onAddClick: () -> Unit,
                      onDonationCardClick: (String) -> Unit) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Perfil") },
                    navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddClick,
                containerColor = Color(0xFF74B895),
                contentColor = Color.White ,
                shape = CircleShape
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Agregar donación"
                )
            }
        },
    ) { innerPadding ->
        ProfileContent(Modifier.padding(innerPadding),
            onEditClick = onEditClick,
            onDonationCardClick = { category ->
                navController.navigate("donation_history/${category}")
            }
        )
    }
}
@Composable
fun ProfileContent(
    modifier: Modifier = Modifier,
    onEditClick: () -> Unit,
    onDonationCardClick: (String) -> Unit
) {
    val verde = colorResource(id = R.color.verde)

    val history = historialDonaciones

    val donationsByCategory = history
        .groupBy { it.categoria }
        .map { (category, list) -> category to list.size }

    val totalDonaciones = history.size

    val fixedCategories = listOf("Alimentos", "Indumentaria", "Electrodomésticos")

    val finalDonationsList = fixedCategories.map { fixedCategory ->
        val count = donationsByCategory.firstOrNull { it.first == fixedCategory }?.second ?: 0
        fixedCategory to count
    }.filter { it.second > 0 }

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
                IconButton(
                    onClick = onEditClick,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .offset(x = (-120).dp, y = (-8).dp)
                        .size(36.dp)
                        .background(Color(0xFF74B895), CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Editar foto",
                        tint = Color.White
                    )
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.width(25.dp))
                    Text("Usuario", fontWeight = FontWeight.Bold, fontSize = 25.sp)
                    Spacer(modifier = Modifier.width(6.dp))
                    Icon(Icons.Default.Star, contentDescription = "Rango", tint = Color(0xFFFFD700),
                        modifier = Modifier
                            .size(25.dp)
                            .offset(y = (-2).dp)
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text("Donaciones: $totalDonaciones", fontSize = 14.sp, color = Color.Black)
            }
            Spacer(modifier = Modifier.height(15.dp))
            // ubicación
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
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
                items(finalDonationsList) { (category, count) ->

                    DonationCard(
                        category,
                        count,
                        onClick = onDonationCardClick
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

        }
    }

@Composable
fun DonationCard(category: String, count: Int,onClick: (String) -> Unit) {
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
            .clickable { onClick(category) },
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
            onEditClick = {},
            onBackClick = {},
            onAddClick = {},
            onDonationCardClick = {}
        )
    }
}
