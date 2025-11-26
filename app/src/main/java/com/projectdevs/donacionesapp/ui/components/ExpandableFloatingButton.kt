package com.projectdevs.donacionesapp.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddBox
import androidx.compose.material3.Icon
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp

@Composable
fun ExpandableFloatingButton(navigateToPost:() -> Unit = {}, navigateToPedido:() -> Unit = {}){
    var expanded by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier.fillMaxSize()
    ) {
        // Sub-items (e.g., smaller FABs)
        AnimatedVisibility(
            visible = expanded,
            enter = fadeIn() + slideInVertically { it },
            exit = fadeOut() + slideOutVertically { it }
        ) {
            Column(horizontalAlignment = Alignment.End) {
                FloatingActionButton(onClick = { navigateToPedido() }) {
                    Row {
                        Box(Modifier.width(100.dp).height(40.dp).padding(10.dp)){
                            Text("Nuevo pedido")
                        }
                        Icon(Icons.Filled.AddBox, "Nuevo pedido.")
                    }
                }
                Spacer(Modifier.height(8.dp))
                FloatingActionButton(onClick = { navigateToPost() }) {
                    Icon(Icons.Filled.Add, "Nueva publicación.")
                }
            }
        }
        Spacer(Modifier.height(16.dp))

        // Main FAB
        FloatingActionButton(
            onClick = { expanded = !expanded }
        ) {
            val rotation by animateFloatAsState(if (expanded) 315f else 0f)
            Icon(
                Icons.Filled.Add,
                "Add",
                modifier = Modifier.rotate(rotation)
            )
        }
    }
}
