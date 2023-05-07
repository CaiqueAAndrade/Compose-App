package com.example.composetest.ui.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions.withCrossFade
import com.bumptech.glide.request.RequestOptions
import com.example.composetest.PhotosViewModel
import com.example.composetest.R
import com.example.composetest.route.Screens
import com.example.composetest.ui.theme.Purple200
import com.example.data_layer.io_retrofit.view_data.Photo
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun PhotoListScreen(navHostController: NavHostController, viewModel: PhotosViewModel) {
    val photos: LazyPagingItems<Photo> = viewModel.photos.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            PhotosListTopBar(viewModel = viewModel)
        }
    ) {
        PhotosListView(
            photos = photos,
            paddingValues = it,
            navHostController = navHostController,
            viewModel = viewModel
        )
    }
}

@Composable
fun PhotosListView(
    photos: LazyPagingItems<Photo>,
    paddingValues: PaddingValues,
    navHostController: NavHostController,
    viewModel: PhotosViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyColumn(contentPadding = paddingValues) {
            items(
                count = photos.itemCount
            ) { index ->
                photos[index]?.let { photo: Photo ->
                    PhotoItem(
                        photo = photo,
                        modifier = Modifier.padding(8.dp),
                        navHostController = navHostController,
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}


@Composable
fun PhotoItem(
    photo: Photo,
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    viewModel: PhotosViewModel
) {
    val description = photo.description ?: photo.altDescription
    Card(
        modifier = modifier
            .clickable {
                viewModel.setPhotoDetail(photo)
                navHostController.navigate(Screens.Details.route)
            },
        elevation = 6.dp,
        shape = MaterialTheme.shapes.small
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            horizontalAlignment = Alignment.Start
        ) {
            ImageLoader(photo.urls.raw, description, modifier.clip(RoundedCornerShape(10.dp)))
            PhotoInformation(photo.user.userName, description, photo.date)
        }
    }
}

@Composable
fun ImageLoader(
    imageURL: String,
    imageDescription: String?,
    modifier: Modifier = Modifier
) {
    GlideImage(
        imageModel = { imageURL },
        modifier = modifier
            .height(188.dp)
            .fillMaxWidth(),
        imageOptions = ImageOptions(
            contentDescription = imageDescription,
            contentScale = ContentScale.Crop
        ),
        requestBuilder = {
            Glide
                .with(LocalView.current)
                .asBitmap()
                .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
                .transition(withCrossFade())
        },
        loading = {
            Box(modifier = Modifier.matchParentSize()) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    )
}

@Composable
fun PhotoInformation(
    author: String,
    description: String?,
    date: String
) {
    Column(modifier = Modifier.padding(horizontal = 12.dp)) {
        Text(
            text = date,
            style = MaterialTheme.typography.subtitle2
        )
        Text(
            text = author,
            style = MaterialTheme.typography.h6
        )
        Text(
            text = description ?: "",
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

@OptIn(ExperimentalTextApi::class)
@Composable
fun PhotosListTopBar(modifier: Modifier = Modifier, viewModel: PhotosViewModel) {
    val inputValue = remember { mutableStateOf(TextFieldValue("")) }
    val gradientColors = listOf(Color.Blue, Purple200 /*...*/)
    val brush = remember {
        Brush.linearGradient(
            colors = gradientColors
        )
    }
    Surface(
        elevation = 8.dp
    ) {
        Row(
            modifier
                .fillMaxWidth()
                .height(68.dp)
                .background(color = Color.White)
        ) {
            TextField(
                value = inputValue.value,
                onValueChange = { value: TextFieldValue -> inputValue.value = value },
                placeholder = { Text(text = stringResource(id = R.string.search)) },
                textStyle = TextStyle(brush = brush),
                colors = TextFieldDefaults.textFieldColors(
                    placeholderColor = Color.DarkGray,
                    disabledPlaceholderColor = Color.Gray,
                    backgroundColor = Color.White
                ),
                modifier = Modifier
                    .padding(horizontal = 32.dp, vertical = 8.dp)
                    .fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.None,
                    autoCorrect = true,
                    keyboardType = KeyboardType.Text,
                ),
                maxLines = 1,
                singleLine = true,
                trailingIcon = {
                    Button(
                        onClick = {
                            viewModel.photos
                        },
                        Modifier.background(Color.Transparent),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                        border = BorderStroke(0.dp, Color.Transparent),
                        elevation = ButtonDefaults.elevation(0.dp)
                    ) {
                        Icon(Icons.Filled.Search, "", modifier, tint = Color.Blue)
                    }
                }
            )
        }
    }
}
