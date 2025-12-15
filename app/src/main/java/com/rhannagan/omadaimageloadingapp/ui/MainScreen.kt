package com.rhannagan.omadaimageloadingapp.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.rhannagan.omadaimageloadingapp.ui.navigation.OmadaImageLoadingNavGraph

@Composable
fun MainScreen(
    navController: NavHostController = rememberNavController()
) {
    OmadaImageLoadingNavGraph(navController)
}