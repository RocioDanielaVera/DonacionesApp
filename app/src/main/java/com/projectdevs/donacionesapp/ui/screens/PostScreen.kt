package com.projectdevs.donacionesapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.EditLocationAlt
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Upload
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.navigation.NavController

//@Preview(heightDp = 1500)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostScreen(
    navController: NavController,
    onBackClick: () -> Unit = {}
) {
    var title by rememberSaveable { mutableStateOf("") }
    var description by rememberSaveable { mutableStateOf("") }
    var itemNumber by rememberSaveable { mutableStateOf("") }
    val itemState = listOf("Nuevo", "Usando")
    val itemCategory = listOf("Indumentaria", "Electrodomesticos", "Gastronomia")
    var expanded by remember { mutableStateOf(false) }
    var expandedCategory by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("") }
    var textFiledSize by remember { mutableStateOf(Size.Zero) }
    val categories = listOf("Alimentos", "Ropa", "Educación", "Salud")
    val locations  = listOf("San Justo", "Morón", "CABA")

    var category by remember { mutableStateOf<String?>(null) }
    var location by remember { mutableStateOf<String?>(null) }

    var showCategorySheet by remember { mutableStateOf(false) }
    var showLocationSheet by remember { mutableStateOf(false) }
    val locationValid = location != null
    val icon = if (expanded) {
        Icons.Filled.KeyboardArrowUp
    } else {
        Icons.Filled.KeyboardArrowDown
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Nueva publicación", fontSize = 17.sp)
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowLeft,
                            contentDescription = null
                        )
                    }
                },
                actions = {
                    Text(
                        modifier = Modifier
                            .clickable(onClick = {
                        navController.popBackStack()
                            })
                            .padding(end = 20.dp),
                        text = "Publicar",
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            )
        },
    ) { paddingValues ->


        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .fillMaxWidth()
                .padding(paddingValues)
                .padding(16.dp),
        ) {

            Column(
                modifier = Modifier
                    .background(Color.LightGray)
                    .fillMaxWidth()
                    .height(180.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    modifier = Modifier
                        .padding(5.dp)
                        .border(1.dp, Color.Gray, RoundedCornerShape(5.dp)),
                    imageVector = Icons.Default.Upload,
                    contentDescription = null,
                    tint = Color.Gray,
                )
                Text(
                    text = "Agregar imágen",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Formatos admitidos: PNG, JPEG/JPG",
                    fontSize = 15.sp,
                    color = Color.Gray
                )
            }
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start,
                text = "Imágenes: 0/10",
                fontSize = 15.sp,
                color = Color.Gray
            )
            Spacer(Modifier.height(20.dp))
            //TITULO

            Text(
                text = "Titulo",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = title,
                onValueChange = { title = it },
                label = {
                    Text(text = "Titulo de la donación")
                }
            )
            Text(
                text = "0/60.",
                fontSize = 13.sp,
                color = Color.DarkGray

            )
            Spacer(Modifier.height(20.dp))
            //DESCRIPCIÓN
            Text(
                text = "Descripción",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Agregue una breve descripción del articulo. Será lo primero que verán posibles donatarios.",
                fontSize = 13.sp,
                color = Color.DarkGray
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth().height(90.dp),
                value = description,
                onValueChange = { description = it },
                label = {
                    Text(text = "Descripción")
                }
            )
            Spacer(Modifier.height(20.dp))
            //CANTIDAD
            Text(
                text = "Cantidad",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = itemNumber,
                onValueChange = { itemNumber = it },
                label = {
                    Text(text = "Número de artículos")
                }
            )
            Spacer(Modifier.height(20.dp))
            //DETALLES
            Text(
                text = "Detalles",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )

            Column {
                OutlinedTextField(
                    value = selectedItem,
                    onValueChange = { selectedItem = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .onGloballyPositioned { coordinates ->
                            textFiledSize = coordinates.size.toSize()
                        },
                    label = {
                        Text(text = "Estado")
                    },
                    trailingIcon = {
                        Icon(icon, "", Modifier.clickable {
                            expanded = !expanded
                        })
                    }

                )
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.width(with(LocalDensity.current) { textFiledSize.width.toDp() })
                ) {
                    itemState.forEach { label ->
                        DropdownMenuItem(text = { Text(text = label) }, onClick = {
                            selectedItem = label
                            expanded = false
                        })
                    }

                }
            }
            Spacer(Modifier.height(20.dp))
            //CATEGORIA
            Text(
                text = "Categoria",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
            Column {
                OutlinedTextField(
                    value = selectedCategory,
                    onValueChange = { selectedCategory = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .onGloballyPositioned { coordinates ->
                            textFiledSize = coordinates.size.toSize()
                        },
                    label = {
                        Text(text = "Seleccionar")
                    },
                    leadingIcon = { Icon(imageVector = Icons.Default.Category, contentDescription = null) },
                    trailingIcon = {
                        Icon(icon, "", Modifier.clickable {
                            expandedCategory = !expandedCategory
                        })
                    }

                )
                DropdownMenu(
                    expanded = expandedCategory,
                    onDismissRequest = { expandedCategory = false },
                    modifier = Modifier.width(with(LocalDensity.current) { textFiledSize.width.toDp() })
                ) {
                    itemCategory.forEach { label ->
                        DropdownMenuItem(text = { Text(text = label) }, onClick = {
                            selectedCategory = label
                            expandedCategory = false
                        })
                    }


                }
            }
            Spacer(Modifier.height(20.dp))

            //PREFERENCIAS DE ENTREGA
            Text(
                text = "Preferencias de entrega",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )

            LabeledPicker(
                label = "",
                value = location ?: "Seleccionar ubicación",
                leadingIcon = {
                    Icon(Icons.Default.EditLocationAlt, contentDescription = null)
                },
                onClick = { showLocationSheet = true },
                isError = !locationValid && location != null
            )

            Spacer(Modifier.height(20.dp))
            //Boton publicar
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                onClick = {
                },
                shape = RoundedCornerShape(10.dp),
                colors =
                    ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                    ),
            ) {
                Text(text = "Publicar", color = MaterialTheme.colorScheme.onPrimary)
            }


        }
    }
}



