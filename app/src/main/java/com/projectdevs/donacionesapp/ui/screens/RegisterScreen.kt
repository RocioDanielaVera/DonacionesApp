package com.projectdevs.donacionesapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.projectdevs.donacionesapp.R
import com.projectdevs.donacionesapp.ui.theme.Green70
import com.projectdevs.donacionesapp.ui.theme.Green90

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun RegisterScreen(navigateToHome: () -> Unit = {}, navigateBack: () -> Unit = {}) {
    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }

    Scaffold(
        modifier = Modifier.background(Color.White).fillMaxSize(),
        topBar = {
            TopAppBar(
                modifier = Modifier.background(Color.White),
                title = { },
                navigationIcon = {
                    IconButton(onClick = { navigateBack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }

    ) { padding ->
        Column(
            modifier = Modifier
                .background(Color.White)
                .padding(paddingValues = padding)
                .padding(16.dp)
                .padding(bottom = 30.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Crea tu cuenta",
                fontSize = 20.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                text = "Ingresa tus datos y forma parte de nuestra comunidad.",
                fontSize = 16.sp,
                color = Color.Gray,
                textAlign = TextAlign.Start
            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = name,
                onValueChange = { name = it },
                label = {
                    Text(
                        text = "Nombre",
                        fontSize = 13.sp
                    )
                },
                leadingIcon = {
                    Icon(
                        modifier = Modifier.size(15.dp),
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        tint = Color.Gray
                    )
                },
            )

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = phone,
                onValueChange = { phone = it },
                label = { Text(text = "Teléfono", fontSize = 13.sp) },
                leadingIcon = {
                    Icon(
                        modifier = Modifier.size(15.dp),
                        imageVector = Icons.Default.Phone,
                        contentDescription = null,
                        tint = Color.Gray
                    )
                },
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = email,
                onValueChange = { email = it },
                label = { Text(text = "Correo",fontSize = 13.sp) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Email,
                        modifier = Modifier.size(15.dp),
                        contentDescription = null,
                        tint = Color.Gray
                    )
                },
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = date,
                onValueChange = { date = it },
                label = { Text(text = "Fecha de nacimiento",fontSize = 13.sp) },
                leadingIcon = {
                    Icon(
                        modifier = Modifier.size(15.dp),
                        imageVector = Icons.Default.DateRange,
                        contentDescription = null,
                        tint = Color.Gray
                    )
                },
            )
            Spacer(Modifier.height(10.dp))

            Button(
                onClick = { navigateToHome() },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Green90,
                    contentColor = Green70
                ),
                content = {
                    Text(
                        text = "Registrarse",
                        fontSize = 12.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                }
            )
            Spacer(Modifier.weight(1f))
            Text(
                modifier = Modifier.fillMaxWidth().padding(end = 20.dp),
                text = stringResource(R.string.Login_privacy_text_end_terms_esp),
                fontSize = 12.sp,
                color = Color.Black,
                textAlign = TextAlign.Center
            )

        }
    }


}