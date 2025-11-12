package com.projectdevs.donacionesapp.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CategoryButton(
    categories: List<String>,
    selectedCategory: String?,
    onSelectedCategory: (String?) -> Unit
) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(8.dp),
    ) {
        categories.forEach { cat ->
            val isSelected = selectedCategory == cat
            Surface(
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, if (isSelected) Color(0xFF74B895) else Color.Gray),
                color = if (isSelected) Color(0xFFDFF3E9) else Color(0xFFF7F7F7),
                tonalElevation = 2.dp,
                modifier = Modifier.clickable {
                    onSelectedCategory(if (isSelected) null else cat)
                }
            ) {
                Text(
                    text = cat,
                    modifier =
                        Modifier
                            .padding(horizontal = 12.dp, vertical = 6.dp),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = if (isSelected) Color(0xFF2D7A58) else Color.Black
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CategoryChipsPreview() {
    MaterialTheme {
        Surface(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            color = Color.White,
        ) {
            CategoryButton (
                categories = listOf("Gastronomía", "Indumentaria", "Electrónica"),
                onSelectedCategory = {},
                selectedCategory = null,
            )
        }
    }
}
