package com.example.appplacarchallengers

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.appplacarchallengers.data.db.ScoreboardDatabase
import com.example.appplacarchallengers.ui.ScoreboardViewModel
import com.example.compose.AppTheme

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
            AppTheme {
                ChallengersApp(viewModel = viewModel)
            }
        }
    }
}