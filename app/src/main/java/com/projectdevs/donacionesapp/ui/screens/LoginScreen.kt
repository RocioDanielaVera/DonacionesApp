package com.projectdevs.donacionesapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.projectdevs.donacionesapp.R
import com.projectdevs.donacionesapp.ui.components.CustomButton
import com.projectdevs.donacionesapp.ui.components.CustomDefaultText
import com.projectdevs.donacionesapp.ui.components.CustomOutlinedTextField
import com.projectdevs.donacionesapp.ui.components.CustomTitle

@Composable
fun LoginScreen(paddingValues: PaddingValues) {
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(Modifier.weight(1f))
        CustomTitle(title = stringResource(R.string.login_welcome_esp))
        Spacer(Modifier.height(10.dp))
        CustomDefaultText(text = stringResource(R.string.login_welcome_description_esp))
        Spacer(Modifier.height(20.dp))
        CustomOutlinedTextField(hint = stringResource(R.string.login_email_or_user_outlined_textfield_esp))
        Spacer(Modifier.height(20.dp))
        CustomOutlinedTextField(hint = stringResource(R.string.login_password_outlined_textfield_esp))
        Spacer(Modifier.height(15.dp))
        CustomButton(value = stringResource(R.string.login_text_button_esp))
        CustomDefaultText(text = stringResource(R.string.login_sign_in_ask_esp))
        Spacer(Modifier.height(10.dp))
        CustomDefaultText(text = stringResource(R.string.login_sign_in_onclick_text_esp))
        Spacer(Modifier.weight(1f))
    }
}
