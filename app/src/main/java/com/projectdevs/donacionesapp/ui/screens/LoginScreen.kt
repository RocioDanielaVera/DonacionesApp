package com.projectdevs.donacionesapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.projectdevs.donacionesapp.R
import com.projectdevs.donacionesapp.ui.components.BottomNavItem
import com.projectdevs.donacionesapp.ui.components.CustomButton
import com.projectdevs.donacionesapp.ui.components.CustomDefaultText
import com.projectdevs.donacionesapp.ui.components.CustomTitle


@Composable
fun LoginScreen(navController: NavController) {
    var password by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }
    var enabledButton by remember { mutableStateOf(false) }
    val icon = if (passwordVisibility) {
        painterResource(id = R.drawable.eye_ic)
    } else {
        painterResource(id = R.drawable.eye_ic)
    }
    Column(
        modifier =
            Modifier
                .background(Color.White)
                .fillMaxSize()
                .padding(20.dp)
                .padding(bottom = 30.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Spacer(Modifier.weight(1f))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Bienvenido",
            fontSize = 40.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(45.dp),
            value = email,
            onValueChange = { email = it },
            label = {
                Text(
                    text = stringResource(R.string.login_email_or_user_outlined_textfield_esp),
                    fontSize = 13.sp
                    )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    modifier = Modifier.size(15.dp),
                    contentDescription = null,
                    tint = Color.Gray
                )
            }
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(45.dp),
            value = password,
            onValueChange = { password = it },
            label = {
                Text(
                    text = stringResource(R.string.login_password_outlined_textfield_esp),
                    fontSize = 13.sp
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    modifier = Modifier.size(15.dp),
                    contentDescription = null,
                    tint = Color.Gray)
            },
            trailingIcon = {
                IconButton(onClick = { passwordVisibility = !passwordVisibility })
                {
                    Icon(
                        painter = icon,
                        modifier = Modifier.size(15.dp),
                        contentDescription = null,
                        tint = Color.Gray
                    )
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation()
        )


        if (!(email.isEmpty() && password.isEmpty())) {
            enabledButton = true
        }
        CustomButton(
            value = stringResource(R.string.login_text_button_esp),
            enabled = enabledButton,
            navigateToScreen = {
                navController.navigate(BottomNavItem.Home.route)
            }
        )

        CustomDefaultText(text = stringResource(R.string.login_sign_in_ask_esp))

        Text(
            modifier = Modifier
                .clickable(onClick = {/* NAVIGATE TO REGISTER */ }),
            text = stringResource(R.string.login_sign_in_onclick_text_esp),
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.titleMedium,
            fontSize = 13.sp,
            textDecoration = TextDecoration.Underline
        )
        Spacer(Modifier.height(130.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.Login_privacy_text_end_terms_esp),
            textAlign = TextAlign.Center,
            fontSize = 12.sp,
        )
    }
}
