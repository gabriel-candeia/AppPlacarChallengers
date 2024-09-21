package com.example.appplacarchallengers

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.appplacarchallengers.ui.theme.AppPlacarChallengersTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppPlacarChallengersTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ChallengersApp(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}