package com.projectdevs.donacionesapp.ui.screens
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.projectdevs.donacionesapp.R
import com.projectdevs.donacionesapp.ui.theme.Green40
import com.projectdevs.donacionesapp.ui.theme.Green70
import com.projectdevs.donacionesapp.ui.theme.Green90

@Preview
@Composable
fun InitialScreen(navigateToLogin:() -> Unit = {}, navigateToRegister:() -> Unit = {}){
    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.weight(1f))
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp),
            painter = painterResource(R.drawable.donation_ilustration),
            contentDescription = null
        )

        Text(
            text = "Forma parte del cambio.",
            fontSize = 45.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Buscamos ayudar a quienes más lo necesiten. \nTu presencia marca la diferencia.",
            fontSize = 12.sp,
            color = Color.Gray,
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(5.dp))

        Button(
            onClick = { navigateToLogin() },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Green40,
                contentColor = Green70
            ),
            content = {
                Text(
                    text = "Ingresar",
                    fontSize = 12.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        )
        Button(
            onClick = { navigateToRegister()},
            modifier = Modifier.fillMaxWidth(),
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
    }
}