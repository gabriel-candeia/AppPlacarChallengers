package com.example.appplacarchallengers.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.appplacarchallengers.R
import com.example.appplacarchallengers.componentsGlobal.AppButton
import com.example.appplacarchallengers.data.DataSource


@Composable
fun ConfigurationScreen(
    getRadioButtonValue: () -> Boolean,
    onRadioButton: () -> Unit,
    getValue: (Int) -> String,
    onValueChange: (Int, String) -> Unit,
    onStartMatchButton: () -> Unit = { },
    modifier: Modifier
) {
    val error = remember { mutableStateListOf<MutableState<Boolean>>() }

    if(error.size != DataSource.configurationOptions.size) {
        error.clear()
        error.addAll(DataSource.configurationOptions.map { remember { mutableStateOf(false) } })
    }

    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp,Alignment.CenterVertically)
    ) {
        DataSource.configurationOptions.forEachIndexed() { index, item ->
            AppTextField(
                value = getValue(item),
                labelResourceId = item,
                onValueChange = {
                    error[index].value = false
                    onValueChange(item, it) },
                isError = error[index].value,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = getRadioButtonValue(),
                onClick = onRadioButton,
            )
            Text("Add timer?")
        }

        AppButton(
            labelResourceId = R.string.button_start_match,
            onClick = {
                var allFieldsValid = true
                DataSource.configurationOptions.forEachIndexed { index, item ->
                    if (getValue(item).isBlank()) {
                        error[index].value = true
                    }
                    allFieldsValid = allFieldsValid && !error[index].value
                }
                if(allFieldsValid) {
                    onStartMatchButton()
                }
            }
        )
    }
}

@Composable
fun AppTextField(
    value: String,
    labelResourceId: Int,
    onValueChange: (String) -> Unit = {_ -> },
    isError: Boolean,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        label = { Text(stringResource(id = labelResourceId)) },
        onValueChange = { onValueChange(it) },
        isError = isError,
        modifier = modifier
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