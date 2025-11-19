package com.projectdevs.donacionesapp.ui.screens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.projectdevs.donacionesapp.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.projectdevs.donacionesapp.ui.theme.DonacionesAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(navController: NavController,
                      onBackClick: () -> Unit = { navController.popBackStack() }) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Editar perfil") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        },
    ) { innerPadding ->
        EditProfileContent(Modifier.padding(innerPadding),
            onBackClick = onBackClick,
            navController = navController
            )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileContent(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    navController: NavController
) {
    val verde = colorResource(id = R.color.verde)
    var nombre by remember { mutableStateOf("Usuario") }
    var ubicacion by remember { mutableStateOf("San Justo") }
    var mail by remember { mutableStateOf("usuario1234@gmail.com") }
    var confirmarGuardado by remember { mutableStateOf(false) }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { }
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                imageVector = Icons.Default.Person,
                contentDescription = "Foto de perfil",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray)
            )
            IconButton(
                onClick = { /* cambiar foto */ },
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

        Spacer(modifier = Modifier.height(32.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre", fontWeight = FontWeight.Bold) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = ubicacion,
                onValueChange = { ubicacion = it  },
                label = { Text("Ubicación", fontWeight = FontWeight.Bold) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = mail,
                onValueChange = { mail = it },
                label = { Text("Mail", fontWeight = FontWeight.Bold) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(24.dp))

            var checked by remember { mutableStateOf(false) }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = checked,
                    onCheckedChange = { checked = it }
                )
                Text(
                    text = "Recibir notificaciones sobre donaciones.",
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween){

                Button(
                    onClick = { confirmarGuardado = true },
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = verde)
                ) {
                    Text("Guardar cambios")
                }
                Button(
                    onClick =  onBackClick,
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) {
                    Text("Cancelar")
                }

            }

            if (confirmarGuardado) {
                AlertDialog(
                    onDismissRequest = { confirmarGuardado = false },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = verde,
                            modifier = Modifier.size(48.dp)
                        )
                    },
                    title = { Text("¿Guardar cambios?", fontWeight = FontWeight.Bold) },
                    text = {
                        Text(
                            "Vas a actualizar los datos de tu perfil.\n¿Querés confirmar los cambios?",
                            color = Color.Gray
                        )
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                confirmarGuardado = false
                                navController.popBackStack()
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = verde)
                        ) {
                            Text("Sí, guardar", color = Color.White)
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { confirmarGuardado = false }) {
                            Text("Cancelar", color = Color.Gray)
                        }
                    },
                    containerColor = Color.White,
                    tonalElevation = 6.dp
                )
            }


        }
    }
}

@Preview(showBackground = true)
@Composable
fun EditProfileScreenPreview() {
    DonacionesAppTheme {
        val navController = rememberNavController()
        EditProfileScreen(
            navController,
            onBackClick = {}
        )
    }
}