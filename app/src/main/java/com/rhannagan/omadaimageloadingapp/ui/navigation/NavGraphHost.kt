package com.rhannagan.omadaimageloadingapp.ui.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.rhannagan.omadaimageloadingapp.ui.feature.home.HomeScreen
import com.rhannagan.omadaimageloadingapp.ui.feature.home.HomeScreenViewModel
import com.rhannagan.omadaimageloadingapp.ui.feature.home.SharedViewModel
import com.rhannagan.omadaimageloadingapp.ui.feature.selectedimage.SelectedImageScreen
import com.rhannagan.omadaimageloadingapp.utils.hiltActivityViewModel

@Composable
fun OmadaImageLoadingNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Destinations.HomeGraph
    ) {
        navigation<Destinations.HomeGraph>(startDestination = Destinations.HomeScreen) {

            composable<Destinations.HomeScreen> {
                val sharedViewModel = hiltActivityViewModel<SharedViewModel>()
                val homeViewModel: HomeScreenViewModel = hiltViewModel()
                HomeScreen(
                    homeScreenViewModel = homeViewModel,
                    sharedViewModel = sharedViewModel,
                    onImageClicked = {
                        navController.navigate(Destinations.SelectedImageScreen)
                    }
                )
            }

            composable<Destinations.SelectedImageScreen> {
                val sharedViewModel = hiltActivityViewModel<SharedViewModel>()
                SelectedImageScreen(
                    sharedViewModel = sharedViewModel
                )
            }
        }
    }
}