package com.example.composetest.route

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.composetest.PhotosViewModel
import com.example.composetest.ui.view.DetailsScreen
import com.example.composetest.ui.view.PhotoListScreen

@Composable
fun SetupNavGraph(
    navHostController: NavHostController,
    photosViewModel: PhotosViewModel
) {
    NavHost(
        navController = navHostController,
        startDestination = Screens.PhotosList.route
    ) {
        composable(
            route = Screens.PhotosList.route
        ) {
            PhotoListScreen(navHostController, photosViewModel)
        }
        composable(
            route = Screens.Details.route
        ) {
            DetailsScreen(navHostController, photosViewModel)
        }
    }
}