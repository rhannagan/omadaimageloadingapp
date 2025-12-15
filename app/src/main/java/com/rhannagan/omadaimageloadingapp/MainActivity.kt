package com.rhannagan.omadaimageloadingapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.rhannagan.omadaimageloadingapp.ui.MainScreen
import com.rhannagan.omadaimageloadingapp.ui.feature.home.HomeScreen
import com.rhannagan.omadaimageloadingapp.ui.theme.OmadaimageloadingappTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OmadaimageloadingappTheme {
                MainScreen()
            }
        }
    }
}