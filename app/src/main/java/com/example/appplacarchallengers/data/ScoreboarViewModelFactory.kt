package com.example.appplacarchallengers.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.appplacarchallengers.data.db.ScoreboardRepository
import com.example.appplacarchallengers.ui.ScoreboardViewModel

/*class ScoreboardViewModelFactory(private val repository: ScoreboardRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ScoreboardViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ScoreboardViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}*/