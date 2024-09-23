package com.example.appplacarchallengers

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.appplacarchallengers.data.db.ScoreboardDatabase
import com.example.appplacarchallengers.ui.ScoreboardViewModel
import com.example.appplacarchallengers.ui.theme.AppPlacarChallengersTheme

class MainActivity : ComponentActivity() {
    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            ScoreboardDatabase::class.java,
            "scoreboard_database"
        ).build()
    }
    private val viewModel by viewModels<ScoreboardViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return ScoreboardViewModel(db.scoreboardDao()) as T
                }
            }
        }
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContent {
            AppPlacarChallengersTheme {
                ChallengersApp(viewModel = viewModel)
            }
        }
    }
}