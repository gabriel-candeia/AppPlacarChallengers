package com.example.appplacarchallengers.ui

import androidx.lifecycle.ViewModel
import com.example.appplacarchallengers.data.Scoreboard
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ScoreboardViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(Scoreboard())
    val uiState: StateFlow<Scoreboard> = _uiState.asStateFlow()

}