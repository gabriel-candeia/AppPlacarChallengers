package com.example.appplacarchallengers.ui

import android.widget.EditText
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.appplacarchallengers.R
import com.example.appplacarchallengers.ui.theme.AppPlacarChallengersTheme

@Composable
fun ConfigurationScreen(
    modifier: Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        AppTextField(
            labelResourceId = R.string.TextField_match_name,
        )

        HorizontalDivider()

        AppTextField(
            labelResourceId = R.string.TextField_player_a1,
        )

        AppTextField(
            labelResourceId = R.string.TextField_player_a2,
        )

        AppTextField(
            labelResourceId = R.string.TextField_player_b1,
        )

        AppTextField(
            labelResourceId = R.string.TextField_player_b2,
        )

        HorizontalDivider()
    }

}

@Composable
fun AppTextField(
    labelResourceId: Int,
    onValueChange: (String) -> Unit = {_ -> },
    modifier: Modifier = Modifier
) {
    TextField(
        value = stringResource(id = labelResourceId),
        onValueChange = { onValueChange(it) }
    )
}

@Preview
@Composable
fun ConfigurationScreenPreview() {
    AppPlacarChallengersTheme {
        ConfigurationScreen(
            modifier = Modifier
                .fillMaxSize()
        )
    }
}