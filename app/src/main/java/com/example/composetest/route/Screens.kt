package com.example.composetest.route

sealed class Screens(val route: String) {
    object PhotosList : Screens("photos_list_screen")
    object Details : Screens("details_screen")
}
