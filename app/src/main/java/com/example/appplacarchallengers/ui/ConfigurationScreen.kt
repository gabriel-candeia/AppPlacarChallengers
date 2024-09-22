package com.example.appplacarchallengers.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.appplacarchallengers.R
import com.example.appplacarchallengers.components.AppButton
import com.example.appplacarchallengers.data.DataSource


@Composable
fun ConfigurationScreen(
    getValue: (Int) -> String,
    onValueChange: (Int, String) -> Unit,
    onStartMatchButton: () -> Unit = { },
    modifier: Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        DataSource.configurationOptions.forEach({ item ->
            AppTextField(
                value = getValue(item),
                labelResourceId = item,
                onValueChange = { onValueChange(item, it) }
            )
        })

        AppButton(
            labelResourceId = R.string.button_start_match,
            onClick = {
                onStartMatchButton()
            }
        )
    }
}

@Composable
fun AppTextField(
    value: String,
    labelResourceId: Int,
    onValueChange: (String) -> Unit = {_ -> },
    modifier: Modifier = Modifier
) {
    TextField(
        value = value,
        label = { Text(stringResource(id = labelResourceId)) },
        onValueChange = { onValueChange(it) }
    )

}

/*

@Composable
fun ConfigurationScreenPreview() {
    AppPlacarChallengersTheme {
        ConfigurationScreen(
            modifier = Modifier
                .fillMaxSize()
        )
    }
}*/