package com.projectdevs.donacionesapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.projectdevs.donacionesapp.R
import com.projectdevs.donacionesapp.domain.Donation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DonationDetailScreen(
    donation: Donation,
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {Text("Detalles de la donacion")},
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF74B895),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Solicitá y enviá un mensaje al donador",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Button(
                    onClick = { /*TODO Solicitar donacion*/},
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(25.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF74B895)
                    )
                ) {
                    Text("Solicitar", color = Color.White)
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(Modifier.height(12.dp))

            Text(
                text = donation.title,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
            )

            Spacer(Modifier.height(12.dp))

            Image(
                painter = painterResource(id = donation.imagen ?: R.drawable.default_donation),
                contentDescription = donation.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(Modifier.height(16.dp))

            Text(
                "Descripción",
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Medium, fontSize = 20.sp)
            )
            Text(
                donation.description,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.DarkGray
            )

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 20.dp),
                thickness = 2.dp,
                color = Color(0xFFE0E0E0)
            )

            Text(
                "Información del donador",
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Medium, fontSize = 20.sp)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFD9D9D9))
                )
                Spacer(Modifier.width(8.dp))
                Column {
                    Text(donation.donorName, fontWeight = FontWeight.SemiBold)
                }
            }

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 20.dp),
                thickness = 2.dp,
                color = Color(0xFFE0E0E0)
            )

            Text(
                "Preferencias de entrega",
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Medium, fontSize = 20.sp)
            )
            Text(
                donation.deliveryPreference,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.DarkGray
            )

            Spacer(Modifier.height(80.dp))
        }
    }
}

@Preview
@Composable
fun DonationDetailScreenPreview(){
    DonationDetailScreen(
        donation = Donation(1, "Comida", "Donación de alimentos no perecederos", "Ulises", ""),
        onBackClick = {},
    )
}