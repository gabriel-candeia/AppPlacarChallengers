package com.example.appplacarchallengers.componentsGlobal

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import com.example.ui.theme.AppTypography

@Composable
fun AppButton(
    labelResourceId: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = AppTypography.titleMedium
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(10)
    ) {
        Text(
            stringResource(labelResourceId),
            style = textStyle
        )
    }
}