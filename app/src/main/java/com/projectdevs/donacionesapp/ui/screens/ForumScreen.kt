package com.projectdevs.donacionesapp.ui.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import kotlin.random.Random

// ----------------- MODELO -----------------
data class DonationRequest(
    val id: String,
    val title: String,
    val summary: String,
    val category: String,         // ej: "Alimentos", "Educación"
    val location: String,         // ej: "CABA", "Morón"
    val timestamp: Long,          // para "Recientes"
    val distanceKm: Double? = null, // para "Cercanía"
    val tags: List<String> = emptyList() // por si quiero mas etiquetas
)

// ----------------- PANTALLA -----------------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DonationRequestsScreen(
    onBack: () -> Unit = {},
    onSelectRequest: (DonationRequest) -> Unit = {},
    onNavigateToCreate: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Estado base: lista mutable
    val requests = remember { sampleRequests().toMutableStateList() }

    // Estado de filtros / orden
    val sortOptions = listOf("Recientes", "Cercanía", "Urgencia")
    var sort by remember { mutableStateOf(sortOptions.first()) }

    val categoryOptions = listOf("Todas", "Alimentos", "Ropa", "Educación", "Salud")
    var category by remember { mutableStateOf("Todas") }

    val locationOptions = listOf("Todas", "La Matanza", "Morón", "CABA", "San Justo")
    var location by remember { mutableStateOf("Todas") }

    // Lista filtrada + ordenada
    val filtered by remember(sort, category, location, requests) {
        derivedStateOf {
            requests
                .asSequence()
                .filter { r -> category == "Todas" || r.category == category }
                .filter { r -> location == "Todas" || r.location == location }
                .let { seq ->
                    when (sort) {
                        "Recientes" -> seq.sortedByDescending { it.timestamp }
                        "Cercanía" -> seq.sortedWith(
                            compareBy<DonationRequest> { it.distanceKm ?: Double.MAX_VALUE }
                                .thenBy { it.title }
                        )

                        "Urgencia" -> seq.sortedBy { urgencyScore(it) } // menor = más urgente (ejemplo)
                        else -> seq
                    }
                }
                .toList()
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Donaciones") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        },
        floatingActionButton = {
            // FAB para agregar dinámicamente una solicitud (demo)
            ExtendedFloatingActionButton(
                // HAY DOS OPCIONES + RANDOM REQUEST O HACER QUE LLEVE A LA +SCREEN DE CREACIÓN
//                onClick = { requests += randomRequest() },
                onClick = onNavigateToCreate,
                icon = { Icon(Icons.Filled.Add, contentDescription = null) },
                text = { Text("Agregar") }
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            // --- Filtros (orden + chips) ---
            Text(
                text = "Filtros",
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Medium),
                modifier = Modifier.padding(start = 16.dp, top = 12.dp, bottom = 6.dp)
            )

            FilterBar(
                sort = sort,
                onSortChange = { sort = it },
                sortOptions = sortOptions,
                category = category,
                onCategoryChange = { category = it },
                categoryOptions = categoryOptions,
                location = location,
                onLocationChange = { location = it },
                locationOptions = locationOptions,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            )

            // --- Contador de resultados ---
//            Text(
//                text = "${filtered.size} solicitudes",
//                style = MaterialTheme.typography.bodySmall,
//                color = MaterialTheme.colorScheme.onSurfaceVariant,
//                modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
//            )

            Divider()

            // --- Lista ---
            if (filtered.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "No encontramos solicitudes con esos filtros.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(top = 12.dp, bottom = 112.dp)
                ) {
                    items(filtered, key = { it.id }) { req ->
                        DonationCard(
                            request = req,
                            onSelect = { onSelectRequest(req) }
                        )
                    }
                }
            }
        }
    }
}

// URGENCIA (ejemplo simple)
private fun urgencyScore(r: DonationRequest): Int {
    // Demo: categorías de salud/educación más urgentes que ropa/alimentos
    val base = when (r.category) {
        "Salud" -> 0
        "Educación" -> 1
        "Alimentos" -> 2
        "Ropa" -> 3
        else -> 4
    }

    return base
}

// FILTROS
@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
private fun FilterBar(
    sort: String,
    onSortChange: (String) -> Unit,
    sortOptions: List<String>,
    category: String,
    onCategoryChange: (String) -> Unit,
    categoryOptions: List<String>,
    location: String,
    onLocationChange: (String) -> Unit,
    locationOptions: List<String>,
    modifier: Modifier = Modifier
) {
    var showCategorySheet by remember { mutableStateOf(false) }
    var showLocationSheet by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .padding(bottom = 6.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // Ordenar por
        SingleChoiceSegmentedButtonRow(
            modifier = Modifier.fillMaxWidth()
        ) {
            sortOptions.forEachIndexed { index, option ->
                SegmentedButton(
                    selected = sort == option,
                    onClick = { onSortChange(option) },
                    shape = SegmentedButtonDefaults.itemShape(index, sortOptions.size),
                    label = { Text(option, maxLines = 1) }
                )
            }
        }

        // Chips (abren sheets)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.weight(1f)
            ) {
                FilterChip(
                    selected = category != "Todas",
                    onClick = { showCategorySheet = true },
                    label = { Text(if (category == "Todas") "Categoría" else "$category") }
                )
                FilterChip(
                    selected = location != "Todas",
                    onClick = { showLocationSheet = true },
                    label = { Text(if (location == "Todas") "Localidad" else "$location") }
                )


                if (category != "Todas" || location != "Todas") {
                    IconButton(
                        onClick = {
                            onCategoryChange("Todas")
                            onLocationChange("Todas")
                        }
                    ) {
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = "Limpiar filtros",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
        }
    }

    if (showCategorySheet) {
        SelectionSheet(
            title = "Categoría",
            options = categoryOptions,
            selected = category,
            onSelect = { onCategoryChange(it) },
            onDismiss = { showCategorySheet = false }
        )
    }
    if (showLocationSheet) {
        SelectionSheet(
            title = "Localidad",
            options = locationOptions,
            selected = location,
            onSelect = { onLocationChange(it) },
            onDismiss = { showLocationSheet = false }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SelectionSheet(
    title: String,
    options: List<String>,
    selected: String,
    onSelect: (String) -> Unit,
    onDismiss: () -> Unit
) {
    ModalBottomSheet(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            options.forEach { opt ->
                ListItem(
                    headlineContent = { Text(opt) },
                    trailingContent = {
                        RadioButton(
                            selected = opt == selected,
                            onClick = { onSelect(opt); onDismiss() }
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onSelect(opt)
                            onDismiss()
                        }
                )
            }
            Spacer(Modifier.height(24.dp))
        }
    }
}

// CARD
@Composable
fun DonationCard(
    request: DonationRequest,
    onSelect: () -> Unit,
    onSeeMore: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        modifier = modifier
            .fillMaxWidth()
            .animateContentSize()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Avatar
                    Surface(
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.08f),
                        modifier = Modifier.size(40.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = request.title.firstOrNull()?.uppercase() ?: "",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }

                    Column {
                        Text(
                            text = request.title,
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                        if (request.location.isNotBlank()) {
                            Text(
                                text = request.location,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }

                OutlinedButton(onClick = onSelect) {
                    Text("Seleccionar")
                }
            }

            //o etiquetas
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                AssistChip(
                    onClick = { },
                    enabled = false,
                    label = { Text(request.category) }
                )
                AssistChip(
                    onClick = { },
                    enabled = false,
                    label = { Text(request.location) }
                )

                request.tags.forEach { tag ->
                    AssistChip(
                        onClick = { },
                        enabled = false,
                        label = { Text(tag) }
                    )
                }
            }

            // Descripción
            Text(
                text = request.summary,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = onSeeMore) {
                    Text("Ver más")
                }
            }
        }
    }
}


@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun AssistChipRow(chips: List<String>) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        chips.forEach { label ->
            AssistChip(onClick = {}, enabled = false, label = { Text(label) })
        }
    }
}

// ----------------- DATA DEMO -----------------
private fun sampleRequests(): List<DonationRequest> = listOf(
    DonationRequest(
        id = "1",
        title = "Comedor Sandra",
        summary = "Alimentos no perecederos para 40 familias. Prioridad: leche, aceite, fideos y arroz.",
        category = "Alimentos",
        location = "San Justo",
        timestamp = System.currentTimeMillis() - 60_000L, // hace 1 min
        distanceKm = 3.2
    ),
    DonationRequest(
        id = "2",
        title = "Escuela Primaria 103",
        summary = "Campaña de útiles: cuadernos, lápices, repuestos A4 y mochilas.",
        category = "Educación",
        location = "Morón",
        timestamp = System.currentTimeMillis() - 3_600_000L, // hace 1 h
        distanceKm = 8.7
    ),
    DonationRequest(
        id = "3",
        title = "Centro de Salud",
        summary = "Gasas, alcohol, guantes y barbijos. También aceptamos donaciones económicas.",
        category = "Salud",
        location = "CABA",
        timestamp = System.currentTimeMillis() - 86_400_000L, // ayer
        distanceKm = 15.4
    ),
    DonationRequest(
        id = "4",
        title = "Ropero Comunitario",
        summary = "Ropa de abrigo para adultos y niños. Botas y frazadas suman mucho.",
        category = "Ropa",
        location = "La Matanza",
        timestamp = System.currentTimeMillis() - 5_000L, // muy reciente
        distanceKm = 1.1
    )
)

// Para demo
private fun randomRequest(): DonationRequest {
    val cats = listOf("Alimentos", "Ropa", "Educación", "Salud")
    val locs = listOf("CABA", "Morón", "La Matanza", "San Justo")
    val cat = cats.random()
    val loc = locs.random()
    val id = Random.nextInt(1000, 9999).toString()
    return DonationRequest(
        id = id,
        title = "Nueva solicitud #$id",
        summary = "Descripción breve de la necesidad. Gracias por colaborar.",
        category = cat,
        location = loc,
        timestamp = System.currentTimeMillis(),
        distanceKm = Random.nextDouble(0.5, 25.0)
    )
}

@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
private fun DonationRequestsScreenPreview() {
    MaterialTheme {
        DonationRequestsScreen(
            onNavigateToCreate = { },
        )
    }
}