package com.example.appplacarchallengers.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.appplacarchallengers.R
import com.example.appplacarchallengers.components.AppButton
import com.example.compose.AppTheme

@Composable
fun HomeScreen(
    onNewMatchButton: () -> Unit = {},
    onPastMatchesButton: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        AppLogo(
            modifier = Modifier.weight(1f)
        )

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            // NewMatchButton
            AppButton(
                labelResourceId = R.string.button_new_match,
                onClick = { onNewMatchButton() },
                modifier = Modifier
            )

            // PastMatchesButton
            AppButton(
                labelResourceId = R.string.button_past_matches,
                onClick = { onPastMatchesButton() },
                modifier = Modifier
            )
        }
    }
}

@Composable
fun AppLogo(
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(R.drawable.app_logo),
        contentDescription = stringResource(R.string.description_app_logo),
        modifier = modifier
    )
}

@Preview
@Composable
fun HomeScreenPreview() {
    AppTheme {
        HomeScreen(
            modifier = Modifier
                .fillMaxSize()
        )
    }
}
