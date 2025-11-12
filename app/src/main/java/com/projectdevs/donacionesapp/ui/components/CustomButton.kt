package com.projectdevs.donacionesapp.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp

@Suppress("ktlint:standard:function-naming")
@Composable
fun CustomButton(value: String) {
    Button(
        modifier = Modifier.fillMaxWidth().height(50.dp),
        onClick = {},
        shape = RectangleShape,
        colors =
            ButtonDefaults.buttonColors(
                containerColor = Color.Cyan,
            ),
    ) {
        Text(text = value)
    }
}
