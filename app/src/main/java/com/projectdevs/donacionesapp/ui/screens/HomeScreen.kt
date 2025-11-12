package com.projectdevs.donacionesapp.ui.screens

import android.R.attr.end
import android.R.attr.text
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.projectdevs.donacionesapp.R
import com.projectdevs.donacionesapp.domain.Donation
import com.projectdevs.donacionesapp.ui.components.CategoryButton
import com.projectdevs.donacionesapp.ui.components.DonationCard
import com.projectdevs.donacionesapp.ui.components.FilterOption
import com.projectdevs.donacionesapp.ui.components.SearchBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    donaciones: List<Donation>,
    onItemClick: (Donation) -> Unit,
    onAddClick: () -> Unit,
) {

    var showFilterDialog by remember { mutableStateOf(false) }
    var selectedFilter by remember { mutableStateOf<String?>(null)}
    var selectedCategory by remember {mutableStateOf<String?>(null) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddClick,
                containerColor = Color(0xFF74B895),
                shape = CircleShape
            ) {
                Icon(Icons.Default.Add, contentDescription = "Agregar donación", tint = Color.White)
            }
        },
    ) { padding ->
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .background(Color(0xFFFDFDFD))
                    .padding(bottom = padding.calculateBottomPadding())
        ) {
            // HEADER
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF74B895))
                    .statusBarsPadding()
                    .padding(vertical = 16.dp, horizontal = 16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.icon_default),
                            contentDescription = "Avatar",
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .border(2.dp, Color.White, CircleShape),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(Modifier.width(12.dp))
                        Text(
                            "Bienvenido, user xxxx",
                            color = Color.White,
                            style = MaterialTheme.typography.titleMedium.copy(fontSize = 18.sp)
                        )
                    }

                    IconButton(onClick = {/* TODO Abrir mensajes */ }) {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = "Mensajes",
                            tint = Color.White,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }

                Spacer(Modifier.height(12.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    SearchBar(
                        value = "",
                        onValueChange = {},
                        placeholder = "Buscar...",
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .height(44.dp)
                    )

                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(Color.White)
                            .clickable { showFilterDialog = true },
                        contentAlignment = Alignment.Center,

                        ) {
                        Icon(
                            Icons.Default.Menu,
                            contentDescription = "Filtros",
                            tint = Color(0xFF74B895)
                        )
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp, horizontal = 16.dp)
            ) {
                Spacer(Modifier.height(8.dp))
                Text("Categorias", style = MaterialTheme.typography.titleSmall)
                Spacer(Modifier.height(8.dp))
                CategoryButton (
                    categories = listOf("Gatronomia", "Indumentaria", "Electronica"),
                    selectedCategory = selectedCategory,
                    onSelectedCategory = { selectedCategory = it }
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text("Sugerencias de", style = MaterialTheme.typography.titleSmall)

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.LocationOn,
                        contentDescription = "Ubicacion",
                        tint = Color(0xFF74B895),
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        "Ubicacion",
                        color = Color(0xFF74B895),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            Spacer(Modifier.height(12.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                contentPadding = PaddingValues(bottom = 100.dp),
            ) {
                items(donaciones) { donacion ->
                    DonationCard(donacion) { onItemClick(donacion) }
                }
            }
        }
    }

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    if (showFilterDialog) {
        ModalBottomSheet(
            onDismissRequest = { showFilterDialog = false },
            sheetState = sheetState
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Ordenar por", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                FilterOption("Populares", selectedFilter) {
                    selectedFilter = it
                    showFilterDialog = false
                }

                Spacer(Modifier.height(16.dp))
                Text("Estado", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                FilterOption("Nuevo", selectedFilter) {
                    selectedFilter = it
                    showFilterDialog = false
                }
                FilterOption("Usado", selectedFilter) {
                    selectedFilter = it
                    showFilterDialog = false
                }

                Spacer(Modifier.height(16.dp))
                Text("Fecha", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                FilterOption("Hoy", selectedFilter) {
                    selectedFilter = it
                    showFilterDialog = false
                }
                FilterOption("Ultimo mes", selectedFilter) {
                    selectedFilter = it
                    showFilterDialog = false
                }
                Spacer(Modifier.height(16.dp))
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    // Lista de ejemplo
    val donaciones =
        listOf(
            Donation(1, "Comida", "Donación de alimentos no perecederos", "Gastronomía", ""),
            Donation(2, "Ropa de abrigo", "Camperas y mantas", "Indumentaria", ""),
            Donation(3, "Celulares", "Dispositivos en buen estado", "Electrónica", ""),
            Donation(4, "Juguetes", "Juguetes para niños", "Otros", ""),
        )

    MaterialTheme {
        HomeScreen(
            onItemClick = {},
            onAddClick = {},
            donaciones = donaciones,
        )
    }
}