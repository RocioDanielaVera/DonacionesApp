package com.projectdevs.donacionesapp.ui.screens

import android.R.attr.end
import android.R.attr.text
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Chat
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
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
import androidx.compose.ui.graphics.Color.Companion.Green
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
import com.projectdevs.donacionesapp.ui.components.LocationButton
import com.projectdevs.donacionesapp.ui.components.SearchBar
import com.projectdevs.donacionesapp.ui.screens.HomeScreen
import com.projectdevs.donacionesapp.ui.theme.Green70

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    donaciones: List<Donation>,
    onItemClick: (Donation) -> Unit,
) {

    var showFilterDialog by remember { mutableStateOf(false) }
    var selectedFilter by remember { mutableStateOf<String?>(null)}
    var selectedCategory by remember {mutableStateOf<String?>(null) }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { padding ->
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .background(Color(0xFFFDFDFD))
        ) {
            // HEADER
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary)
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
                            painter = painterResource(id = R.drawable.foto_perfil),
                            contentDescription = "Avatar",
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .border(2.dp, Color.White, CircleShape),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(Modifier.width(12.dp))
                        Text(
                            "Bienvenido, Juan López",
                            color = Color.White,
                            style = MaterialTheme.typography.titleMedium.copy(fontSize = 18.sp)
                        )
                    }

                    IconButton(onClick = {/* TODO Abrir mensajes */ }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.Chat,
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
                            .weight(1f)
                            .height(50.dp)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .clip(shape = RoundedCornerShape(24.dp))
                            .background(Color.White)
                            .clickable { showFilterDialog = true },
                        contentAlignment = Alignment.Center,

                        ) {
                        Icon(
                            Icons.Default.FilterList,
                            contentDescription = "Filtros",
                            tint = MaterialTheme.colorScheme.secondaryContainer
                        )
                    }
                }
            }


            val filteredDonations = donaciones.filter { donation ->
                selectedCategory == null || donation.category == selectedCategory
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 2.dp, horizontal = 10.dp)
            ) {
                Spacer(Modifier.height(8.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    CategoryButton(
                        categories = listOf("Gatronomia", "Indumentaria", "Electronica", "Otros"),
                        selectedCategory = selectedCategory,
                        onSelectedCategory = { selectedCategory = it }
                    )
                }
            }

            var abrirSheet by remember { mutableStateOf(false)}

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text("Sugerencias por ubicación", style = MaterialTheme.typography.titleSmall)

                LocationButton(
                    location = "San justo - 65km",
                    onClick = {abrirSheet = true}
                )

                if (abrirSheet) {
                    LocationSheet(
                        ubicacionActual = "San justo - 65km",
                        ubicacionesRecientes = listOf(
                            "Rafael Castillo 1755CP - Buenos Aires",
                            "San Justo - Buenos Aires"
                        ),
                        onUbicacionSeleccionada = {abrirSheet = false},
                        onClose = {abrirSheet = false}
                    )
                }
            }

            Spacer(Modifier.height(10.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                contentPadding = PaddingValues(bottom = 100.dp),
            ) {
                items(filteredDonations) { donacion ->
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationSheet(
    ubicacionesRecientes: List<String>,
    ubicacionActual: String,
    onUbicacionSeleccionada: (String) -> Unit,
    onClose: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onClose,
        sheetState = rememberModalBottomSheetState()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(onClick = onClose) {
                    Icon(Icons.Default.Close, contentDescription = "Cerrar")
                }

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = "Elige una ubicación",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.weight(1f))

                Icon(Icons.Default.Search, contentDescription = "Buscar")
            }

            Spacer(Modifier.height(16.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .border(
                        width = 2.dp,
                        color = Color.Black,
                        shape = RoundedCornerShape(12.dp),
                    )
                    .padding(10.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.mapa_estatico),
                    contentDescription = "Mapa",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    ubicacionActual,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Gray
                )

                Spacer(Modifier.height(8.dp))
            }

            Spacer(Modifier.height(24.dp))

            Text(
                text = "Ubicaciones elegidas recientemente",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(Modifier.height(12.dp))

            ubicacionesRecientes.forEach { ubicacion ->
                UbicacionItem(
                    texto = ubicacion,
                    onClick = { onUbicacionSeleccionada(ubicacion)}
                )
                Spacer(Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun UbicacionItem(
    texto: String,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .border(
                width = 2.dp,
                color = Color.Black,
                shape = RoundedCornerShape(12.dp)
            ),
    ) {
        Icon(
            imageVector = Icons.Default.LocationOn,
            contentDescription = null,
            tint = Color.Gray,
            modifier = Modifier.padding(10.dp)
        )

        Spacer(Modifier.width(8.dp))

        Text(
            text = texto,
            style = MaterialTheme.typography.titleMedium,
            color = Color.Gray
        )
    }
}


//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun HomeScreenPreview() {
//    // Lista de ejemplo
//    val donaciones =
//        listOf(
//            Donation(1, "Comida", "Donación de alimentos no perecederos", "Gastronomía", ""),
//            Donation(2, "Ropa de abrigo", "Camperas y mantas", "Indumentaria", ""),
//            Donation(3, "Celulares", "Dispositivos en buen estado", "Electrónica", ""),
//            Donation(4, "Juguetes", "Juguetes para niños", "Otros", ""),
//        )
//
//    MaterialTheme {
//        HomeScreen(
//            onItemClick = {},
//            onAddClick = {},
//            donaciones = donaciones,
//        )
//    }
//}