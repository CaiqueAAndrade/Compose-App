package com.example.composetest.ui.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.composetest.PhotosViewModel

@Composable
fun DetailsScreen(navHostController: NavHostController, photosViewModel: PhotosViewModel) {
    Scaffold(
        topBar = {
            DetailsTopBar(
                navHostController = navHostController,
                photosViewModel.photo.value?.user?.userName ?: ""
            )
        }
    ) {
        DetailsView(photosViewModel = photosViewModel, it)
    }
}

@Composable
fun DetailsView(photosViewModel: PhotosViewModel, paddingValues: PaddingValues) {
    val photo = photosViewModel.photo.value
    val description = (photo?.description ?: photo?.altDescription) ?: ""
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
    ) {
        ImageLoader(
            imageURL = photo?.urls?.raw ?: "",
            imageDescription = description,
            modifier = Modifier
                .fillMaxWidth()
                .height(386.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        PhotoInformation(
            author = photo?.user?.userName ?: "",
            description = description,
            date = photo?.date ?: ""
        )
    }
}

@Composable
fun DetailsTopBar(navHostController: NavHostController, name: String) {
    Surface(
        elevation = 8.dp
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .height(68.dp)
                .background(color = Color.White),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = { navHostController.popBackStack() },
                Modifier.background(Color.Transparent),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                border = BorderStroke(0.dp, Color.Transparent),
                elevation = ButtonDefaults.elevation(0.dp)
            ) {
                Icon(Icons.Filled.Close, "Close", tint = Color.Blue)
            }
            Text(
                text = name,
                style = MaterialTheme.typography.h6,
                color = Color.Blue,
                modifier = Modifier
                    .padding(start = 32.dp)
            )
        }
    }
}