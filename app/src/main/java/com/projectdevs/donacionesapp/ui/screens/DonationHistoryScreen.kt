package com.projectdevs.donacionesapp.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.projectdevs.donacionesapp.R
import com.projectdevs.donacionesapp.domain.historialDonaciones
import com.projectdevs.donacionesapp.ui.theme.DonacionesAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DonationHistoryScreen(navController: NavController,
                  category: String,
                          onBackClick: () -> Unit = { navController.popBackStack() }) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Histórico") },
                navigationIcon = {
                    IconButton(onClick = onBackClick ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        },
    ) { innerPadding ->
        DonationHistoryContent(Modifier.padding(innerPadding),
            category = category,
            navController = navController
        )
    }
}

@SuppressLint("SuspiciousIndentation")
@Composable
fun DonationHistoryContent(
    modifier: Modifier = Modifier,
    category: String, navController: NavController) {
    val verdeFondo = colorResource(id = R.color.verdeFondo)
    val donationsFinalizadas = historialDonaciones

    val filteredDonations = donationsFinalizadas.filter { it.categoria == category }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        item {
            Text(
                category,
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        items(filteredDonations) { item ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                elevation = CardDefaults.cardElevation(4.dp),
                colors = CardDefaults.cardColors(containerColor = verdeFondo),
                shape = RoundedCornerShape(8.dp),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)){
                        Text(item.nombre, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("Receptor: ${item.receptor}", color = Color.DarkGray, fontSize = 13.sp)
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(horizontalAlignment = Alignment.End) {
                        Text(item.estado, fontWeight = FontWeight.SemiBold, color = Color(0xFF247A49))
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(item.fecha, color = Color.Gray, fontSize = 13.sp)
                    }
                }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun DonationHistoryPreview() {
    DonacionesAppTheme {
        val navController = rememberNavController()
        DonationHistoryScreen(
            navController = navController,
            category = "Alimentos"
        )
    }
}