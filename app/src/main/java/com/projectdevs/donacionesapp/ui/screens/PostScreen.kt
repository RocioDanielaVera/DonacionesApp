package com.projectdevs.donacionesapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowUp
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import com.projectdevs.donacionesapp.R
import com.projectdevs.donacionesapp.ui.components.CustomButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostScreen(innerPadding: PaddingValues) {
    var title by rememberSaveable { mutableStateOf("") }
    var description by rememberSaveable { mutableStateOf("") }
    var preferences by rememberSaveable { mutableStateOf("") }
    val itemState = listOf("Nuevo", "Usando")
    val itemCategory = listOf("Indumentaria", "Electrodomesticos", "Gastronomia")
    var expanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("") }
    var textFiledSize by remember { mutableStateOf(Size.Zero) }

    val icon = if (expanded) {
        Icons.Filled.KeyboardArrowUp
    } else {
        Icons.Filled.KeyboardArrowDown
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Nueva publicación", fontSize = 18.sp)
                },
                navigationIcon = {
                    IconButton(onClick = {
                    }) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowLeft,
                            contentDescription = null
                        )
                    }
                },
                actions = {
                    Text(
                        modifier = Modifier
                            .clickable(onClick = {})
                            .padding(end = 20.dp),
                        text = "Publicar",
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                //Avatar
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(shape = CircleShape)
                        .background(Color.Gray)
                )
                Spacer(Modifier.width(10.dp))
                Text(text = "User 1234")
            }
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                //Agregar imágen
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .height(80.dp)
                        .background(Color.LightGray),
                )
                Text(text = "Agregar imágen")
                //Titulo
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = title,
                    onValueChange = { title = it },
                    label = {
                        Text(text = "Titulo")
                    }
                )
                //Estado del producto
                Column(modifier = Modifier.padding(20.dp)) {
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


                //Descripción
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = description,
                    onValueChange = { description = it },
                    label = {
                        Text(text = "Descripción")
                    }
                )
                //Preferencias de entrega
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = preferences,
                    onValueChange = { preferences = it },
                    label = {
                        Text(text = "Preferencias de entrega")
                    }
                )
                //Categoria
                Column(modifier = Modifier.padding(20.dp)) {
                    OutlinedTextField(
                        value = selectedCategory,
                        onValueChange = { selectedCategory = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .onGloballyPositioned { coordinates ->
                                textFiledSize = coordinates.size.toSize()
                            },
                        label = {
                            Text(text = "Categorias")
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
                        itemCategory.forEach { label ->
                            DropdownMenuItem(text = { Text(text = label) }, onClick = {
                                selectedCategory= label
                                expanded = false
                            })
                        }


                    }
                }


                //Titulo ubicación
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Ubicacion",
                    textAlign = TextAlign.Start
                )
                //Descripción de ubicación
                Text(text = stringResource(R.string.post_location_help_context_message))
                Row {
                    Text(text = "La Matanza xxx -")
                    Spacer(Modifier.weight(1f))
                    Text(text = "Editar")
                }
                //Boton publicar
                CustomButton(
                    value = stringResource(R.string.login_text_button_esp),
                    enabled = true
                )
            }

        }
    }
}



