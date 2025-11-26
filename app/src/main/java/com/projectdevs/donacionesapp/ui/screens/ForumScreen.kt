package com.projectdevs.donacionesapp.ui.screens

import android.R.attr.padding
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import kotlin.random.Random

// ----------------- MODELO -----------------
data class DonationRequest(
    val id: String,
    val title: String,              // Ej: "Comedor Sandra"
    val summary: String,            // Lo que se necesita (corto)
    val category: String,
    val location: String,

    val quantity: Int? = null,      // Cantidad solicitada
    val condition: String? = null,  // "Nuevo", "Usado", "Ninguno"
    val urgency: String? = null,    // "Alta", "Media", "Baja"

    val fullDescription: String? = null, // Texto largo
    val tags: List<String> = emptyList(),

    val timestamp: Long,
    val distanceKm: Double?
)

// ----------------- PANTALLA -----------------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DonationRequestsScreen(
    onBack: () -> Unit = {},
    onSelectRequest: (DonationRequest) -> Unit = {},
    onNavigateToChat:() -> Unit,
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
        containerColor = Color(0xFFF5F8F6),
        topBar = {
            TopAppBar(
                title = { Text("Pedidos de Donaciones") },
//                navigationIcon = {
//                    IconButton(onClick = onBack) {
//                        Icon(Icons.Filled.ArrowBack, contentDescription = "Volver")
//                    }
//                }
            )
        },
//        floatingActionButton = {
//            // FAB para agregar dinámicamente una solicitud (demo)
//            ExtendedFloatingActionButton(
//                // HAY DOS OPCIONES + RANDOM REQUEST O HACER QUE LLEVE A LA +SCREEN DE CREACIÓN
////                onClick = { requests += randomRequest() },
//                onClick = onNavigateToCreate,
//                icon = { Icon(Icons.Filled.Add, contentDescription = null) },
//                text = { Text("Nuevo Pedido") },
////                containerColor = Color(0xFF4BB053),
//                containerColor = MaterialTheme.colorScheme.primary,
//                contentColor = Color.Black
//            )
//        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
                    end = innerPadding.calculateEndPadding(LocalLayoutDirection.current))
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
                        .weight(1f)
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(top = 12.dp, bottom = 112.dp)
                ) {
                    items(filtered, key = { it.id }) { req ->
                        DonationCard(
                            request = req,
                            onContact = {  onNavigateToChat()}
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
                    label = { Text(option, maxLines = 1) },
                    colors = SegmentedButtonDefaults.colors(
                        activeContainerColor = Color(0xFF4BB053),
                        activeContentColor = Color.Black,
                        inactiveContainerColor = MaterialTheme.colorScheme.surface,
                        inactiveContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
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
    onContact: () -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = Color(0xFFE0F2E9),
                shape = RoundedCornerShape(12.dp)
            )
    ) {
        ElevatedCard(
            modifier = modifier
                .fillMaxWidth()
                .animateContentSize()
                .clickable { expanded = !expanded },
            colors = CardDefaults.elevatedCardColors(
                containerColor = Color(0xFFFDFDFD),
            ),
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                if (!request.urgency.isNullOrBlank()) {
                    UrgencyChip(request.urgency)
                }

                Text(
                    text = request.summary,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium),
                    maxLines = if (expanded) Int.MAX_VALUE else 3,
                    overflow = TextOverflow.Ellipsis
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    // Usuario
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = Color(0xFF2E7D32).copy(alpha = 0.12f),
                            modifier = Modifier.size(40.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = request.title.firstOrNull()?.uppercase() ?: "",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = Color(0xFF003366)
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

                    // Botón Contactar
                    OutlinedButton(
                        onClick = onContact,
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Color(0xFF2E7D32)
                        ),
                        border = BorderStroke(
                            width = 1.dp,
                            color = Color(0xFF2E7D32)
                        )
                    ) {
                        Text("Contactar")
                    }
                }

                // cantidad, descripción larga
                if (expanded) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(6.dp),
                        modifier = Modifier.padding(top = 4.dp)
                    ) {
                        if (request.quantity != null) {
                            Text(
                                text = "Cantidad: ${request.quantity}",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                        if (!request.condition.isNullOrBlank()) {
                            Text(
                                text = "Estado: ${request.condition}",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                        val full = request.fullDescription
                        if (!full.isNullOrBlank()) {
                            Text(
                                text = full,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }

                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    AssistChip(
                        onClick = {},
                        enabled = false,
                        label = { Text(request.category) }
                    )
                    AssistChip(
                        onClick = {},
                        enabled = false,
                        label = { Text(request.location) }
                    )
                    request.tags.forEach { tag ->
                        AssistChip(
                            onClick = {},
                            enabled = false,
                            label = { Text(tag) }
                        )
                    }
                }

//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.End
//            ) {
//                TextButton(onClick = { expanded = !expanded }) {
//                    Text(if (expanded) "Ver menos" else "Ver más")
//                }
//            }
            }
        }
    }
}

@Composable
private fun UrgencyChip(urgency: String) {
    val (bg, fg, label) = when (urgency.lowercase()) {
        "alta" -> Triple(
            Color(0xFFFFEBEE),
            Color(0xFFD32F2F),
            "Alta urgencia"
        )

        "media" -> Triple(
            Color(0xFFFFF8E1),
            Color(0xFFF9A825),
            "Urgencia media"
        )

        "baja" -> Triple(
            Color(0xFFE8F5E9),
            Color(0xFF2E7D32),
            "Urgencia baja"
        )

        else -> Triple(
            MaterialTheme.colorScheme.surfaceVariant,
            MaterialTheme.colorScheme.onSurfaceVariant,
            urgency
        )
    }

    Surface(
        color = bg,
        contentColor = fg,
        shape = RoundedCornerShape(50),
        modifier = Modifier
            .heightIn(min = 24.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .background(fg, CircleShape)
            )
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Medium)
            )
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
            AssistChip(
                onClick = {},
                enabled = false,
                label = { Text(label) },
                border = BorderStroke(1.dp, Color(0xFF2E7D32)),
                colors = AssistChipDefaults.assistChipColors(
                    labelColor = Color(0xFF2E7D32)
                ),
            )
        }
    }
}

// ----------------- DATA DEMO -----------------
private fun sampleRequests(): List<DonationRequest> = listOf(
    DonationRequest(
        id = "1",
        title = "Comedor Sandra",
        summary = "Alimentos no perecederos para 40 familias.",
        category = "Alimentos",
        location = "San Justo",
        quantity = 40,
        condition = "Ninguno",
        urgency = "Alta",
        fullDescription = "Necesitamos alimentos no perecederos para abastecer a 40 familias durante el mes. Prioridad: leche en polvo, aceite, fideos, arroz y conservas.",
        tags = listOf("Urgente", "Comunidad"),
        timestamp = System.currentTimeMillis() - 60_000L,
        distanceKm = 3.2
    ),
    DonationRequest(
        id = "2",
        title = "Escuela Primaria 103",
        summary = "Campaña de útiles: cuadernos, lápices y mochilas.",
        category = "Educación",
        location = "Morón",
        quantity = 25,
        condition = "Nuevo",
        urgency = "Media",
        fullDescription = "La escuela está organizando una campaña para alumnos de bajos recursos. Se buscan útiles escolares: cuadernos de tapa dura, lápices, repuestos A4 y mochilas resistentes.",
        tags = listOf("Niños", "Educación"),
        timestamp = System.currentTimeMillis() - 3_600_000L,
        distanceKm = 8.7
    ),
    DonationRequest(
        id = "3",
        title = "Centro de Salud",
        summary = "Gasas, alcohol, guantes y barbijos.",
        category = "Salud",
        location = "CABA",
        quantity = null,
        condition = "Nuevo",
        urgency = "Alta",
        fullDescription = "El centro de salud está con faltante crítico de insumos básicos. Se necesitan gasas estériles, alcohol 70°, guantes descartables y barbijos quirúrgicos.",
        tags = listOf("Urgencia"),
        timestamp = System.currentTimeMillis() - 86_400_000L,
        distanceKm = 15.4
    ),
    DonationRequest(
        id = "4",
        title = "Ropero Comunitario",
        summary = "Ropa de abrigo para adultos y niños.",
        category = "Ropa",
        location = "La Matanza",
        quantity = 30,
        condition = "Usado",
        urgency = "Baja",
        fullDescription = "Se necesitan camperas, buzos, pantalones térmicos y frazadas para adultos y niños. También aceptamos botas, guantes y gorros de invierno.",
        tags = listOf("Invierno"),
        timestamp = System.currentTimeMillis() - 5_000L,
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
//            onNavigateToCreate = { },
            onNavigateToChat = {}
        )
    }
}