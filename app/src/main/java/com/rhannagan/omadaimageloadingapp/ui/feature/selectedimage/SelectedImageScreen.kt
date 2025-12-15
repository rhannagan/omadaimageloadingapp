package com.rhannagan.omadaimageloadingapp.ui.feature.selectedimage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.rhannagan.omadaimageloadingapp.ui.feature.home.SharedViewModel

@Composable
fun SelectedImageScreen(sharedViewModel: SharedViewModel) {

    val selectedPhotoState = sharedViewModel.selectedPhoto.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(selectedPhotoState.value.imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = selectedPhotoState.value.title,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .wrapContentSize()
            )
            Spacer(modifier = Modifier.fillMaxWidth().padding(8.dp))
            Text(
                text = selectedPhotoState.value.title,
                fontSize = 18.sp
            )
        }
    }
}