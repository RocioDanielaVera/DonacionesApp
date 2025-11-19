package com.projectdevs.donacionesapp.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.LocalDining
import androidx.compose.material.icons.filled.Checkroom
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.EditLocationAlt
import androidx.compose.material3.Divider
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.tensorflow.lite.support.label.Category

// Modelo de creación
data class DonationDraft(
    val category: String?,
    val location: String?,
    val title: String,
    val description: String,
    val quantity: Int?,
    val condition: String?,  // Estado: Nuevo / Usado / Ninguno
    val urgency: String?     // Baja / Media / Alta
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DonationCreateScreen(
    onClose: () -> Unit = {},
    onCreate: (DonationDraft) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val focus = LocalFocusManager.current

    val categories = listOf("Alimentos", "Ropa", "Educación", "Salud")
    val locations  = listOf("Florencio Varela 1903", "San Justo", "Morón", "CABA")

    var category by remember { mutableStateOf<String?>(null) }
    var location by remember { mutableStateOf<String?>(null) }
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    var quantityText by remember { mutableStateOf("") }
    var condition by remember { mutableStateOf<String?>(null) } // Estado
    var urgency by remember { mutableStateOf<String?>(null) }   // Nivel de urgencia

    var showCategorySheet by remember { mutableStateOf(false) }
    var showLocationSheet by remember { mutableStateOf(false) }

    // VALIDACIONES
    val titleValid = title.trim().length in 6..60
    val descValid  = description.trim().length in 1..120
    val categoryValid = category != null
    val locationValid = location != null

    val quantity: Int? = quantityText.toIntOrNull()
    val quantityValid = quantity == null || quantity > 0

    val formValid = titleValid && descValid && categoryValid && locationValid && quantityValid

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Solicitar Donación") },
                navigationIcon = {
                    IconButton(onClick = onClose) {
                        Icon(Icons.Default.Close, contentDescription = "Cerrar")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                LabeledPicker(
                    label = "Categoría",
                    value = category ?: "Seleccionar",
                    leadingIcon = {
                        Icon(
                            categoryIcon(category),
                            contentDescription = "Categoría seleccionada"
                        )
                    },
                    onClick = { showCategorySheet = true },
                    isError = !categoryValid && category != null
                )

                LabeledPicker(
                    label = "Localidad / Dirección",
                    value = location ?: "Seleccionar",
                    leadingIcon = {
                        Icon(Icons.Default.EditLocationAlt, contentDescription = null)
                    },
                    onClick = { showLocationSheet = true },
                    isError = !locationValid && location != null
                )

                OutlinedTextField(
                    value = title,
                    onValueChange = { if (it.length <= 60) title = it },
                    label = { Text("Título de la donación") },
                    singleLine = true,
                    isError = title.isNotEmpty() && !titleValid,
                    supportingText = {
                        val msg = if (title.isNotEmpty() && !titleValid)
                            "Mín. 6 y máx. 60 caracteres"
                        else "${title.length}/60"
                        Text(msg)
                    },
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Sentences,
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = "Descripción",
                        style = MaterialTheme.typography.titleSmall
                    )
                    Text(
                        text = "Agregue una breve descripción del artículo a solicitar. Será lo primero que verán posibles donantes cercanos a tu ubicación.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    OutlinedTextField(
                        value = description,
                        onValueChange = { if (it.length <= 120) description = it },
                        placeholder = { Text("Descripción") },
                        minLines = 4,
                        maxLines = 6,
                        isError = description.isNotEmpty() && !descValid,
                        supportingText = {
                            val msg = if (description.isNotEmpty() && !descValid)
                                "Máx. 120 caracteres"
                            else "${description.length}/120 caracteres"
                            Text(msg)
                        },
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Sentences,
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 120.dp)
                    )
                }

                // --------- CANTIDAD ----------
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = "Cantidad",
                        style = MaterialTheme.typography.titleSmall
                    )
                    OutlinedTextField(
                        value = quantityText,
                        onValueChange = { new ->
                            if (new.length <= 4 && new.all { it.isDigit() }) {
                                quantityText = new
                            }
                        },
                        placeholder = { Text("Número de artículos") },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next
                        ),
                        isError = !quantityValid,
                        supportingText = {
                            if (!quantityValid) {
                                Text("Debe ser un número mayor a 0")
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                // --------- DETALLES DESEABLES: ESTADO ----------
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = "Detalles deseables",
                        style = MaterialTheme.typography.titleSmall
                    )
                    OutlinedDropdownField(
                        label = "Estado",
                        value = condition ?: "Estado",
                        options = listOf("Nuevo", "Usado", "Ninguno"),
                        onValueChange = { condition = it }
                    )
                }

                // --------- URGENCIA ----------
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = "Urgencia",
                        style = MaterialTheme.typography.titleSmall
                    )
                    Text(
                        text = "Seleccione el nivel de urgencia de su pedido. Los pedidos de alto nivel se verán en el inicio del foro.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    OutlinedDropdownField(
                        label = "Nivel de urgencia",
                        value = urgency ?: "Nivel de urgencia",
                        options = listOf("Baja", "Media", "Alta"),
                        onValueChange = { urgency = it }
                    )
                }

                Divider(modifier = Modifier.padding(top = 8.dp))

                // --------- BOTÓN CREAR ----------
                FilledTonalButton(
                    onClick = {
                        focus.clearFocus()
                        onCreate(
                            DonationDraft(
                                category = category,
                                location = location,
                                title = title.trim(),
                                description = description.trim(),
                                quantity = quantity,
                                condition = condition,
                                urgency = urgency
                            )
                        )
                    },
                    enabled = formValid,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp)
                        .height(48.dp)
                ) {
                    Text("Crear", style = MaterialTheme.typography.labelLarge)
                }
            }
        }
    }

    if (showCategorySheet) {
        SelectionSheet(
            title = "Categoría",
            options = categories,
            selected = category,
            onSelect = { category = it },
            onDismiss = { showCategorySheet = false }
        )
    }
    if (showLocationSheet) {
        SelectionSheet(
            title = "Localidad / Dirección",
            options = locations,
            selected = location,
            onSelect = { location = it },
            onDismiss = { showLocationSheet = false }
        )
    }
}

@Composable
private fun LabeledPicker(
    label: String,
    value: String,
    leadingIcon: @Composable (() -> Unit)? = null,
    onClick: () -> Unit,
    isError: Boolean = false
) {
    Column(Modifier.fillMaxWidth()) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(bottom = 6.dp)
        )
        Surface(
            tonalElevation = 0.dp,
            shape = MaterialTheme.shapes.medium,
            border = DividerDefaults.color.let {
                BorderStroke(
                    1.dp,
                    if (isError) MaterialTheme.colorScheme.error else it
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .clickable(onClick = onClick)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                if (leadingIcon != null) leadingIcon()
                Text(
                    text = value,
                    style = MaterialTheme.typography.bodyLarge,
                    color = if (value == "Seleccionar")
                        MaterialTheme.colorScheme.onSurfaceVariant
                    else MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SelectionSheet(
    title: String,
    options: List<String>,
    selected: String?,
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
                        RadioButton(selected = (opt == selected), onClick = {
                            onSelect(opt); onDismiss()
                        })
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onSelect(opt); onDismiss()
                        }
                )
            }
            Spacer(Modifier.height(24.dp))
        }
    }
}

fun categoryIcon(category: String?): ImageVector {
    return when (category) {
        "Alimentos" -> Icons.Default.LocalDining
        "Ropa" -> Icons.Default.Checkroom
        "Educación" -> Icons.Default.School
        "Salud" -> Icons.Default.MedicalServices
        else -> Icons.Default.Category
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OutlinedDropdownField(
    label: String,
    value: String,
    options: List<String>,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        OutlinedTextField(
            readOnly = true,
            value = value,
            onValueChange = {},
            label = { Text(label) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onValueChange(option)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
private fun DonationCreateScreenPreview() {
    MaterialTheme {
        DonationCreateScreen()
    }
}
