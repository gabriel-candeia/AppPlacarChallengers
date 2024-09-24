package com.example.appplacarchallengers.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun GameDialog(
    title: String,
    icon: @Composable (() -> Unit)? = null,
    confirmButtonText: String = "Confirm",
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        icon = icon,
        title = { Text(text = title) },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(text = confirmButtonText)
            }
        },
        onDismissRequest = onDismiss
    )
}
