package com.projectdevs.donacionesapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
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
                .fillMaxSize()
                .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Spacer(Modifier.weight(1f))
        //Titulo
        CustomTitle(title = stringResource(R.string.login_welcome_esp))

        Spacer(Modifier.height(10.dp))
        //Descripción
        CustomDefaultText(text = stringResource(R.string.login_welcome_description_esp))

        Spacer(Modifier.height(20.dp))

        //Correo
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = email,
            onValueChange = { email = it },
            label = {
                Text(text = stringResource(R.string.login_email_or_user_outlined_textfield_esp))
            },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Email, contentDescription = null)
            }
        )

        Spacer(Modifier.height(20.dp))

        //Contraseña
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = password,
            onValueChange = { password = it },
            label = { Text(text = stringResource(R.string.login_password_outlined_textfield_esp)) },
            leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = null) },
            trailingIcon = {
                IconButton(onClick = { passwordVisibility = !passwordVisibility })
                {
                    Icon(
                        painter = icon,
                        contentDescription = null
                    )
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation()
        )

        Spacer(Modifier.height(15.dp))

        //Boton de iniciar sesión
        if (!(email.isEmpty() && password.isEmpty())) {
            enabledButton = true
        }
        CustomButton(
            value = stringResource(R.string.login_text_button_esp),
            enabled = enabledButton,
            navigateToHomeScreen = {
                navController.navigate(BottomNavItem.Home.route)
            }
        )

        Spacer(Modifier.height(30.dp))

        //Pregunta
        CustomDefaultText(text = stringResource(R.string.login_sign_in_ask_esp))

        Spacer(Modifier.height(10.dp))

        //Registrarse
        Text(
            modifier = Modifier
                .clickable(onClick = {}),
            text = stringResource(R.string.login_sign_in_onclick_text_esp),
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(Modifier.weight(1f))
        //Terminos
        Text(
            text = stringResource(R.string.Login_privacy_text_end_terms_esp),
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(30.dp))
    }
}
