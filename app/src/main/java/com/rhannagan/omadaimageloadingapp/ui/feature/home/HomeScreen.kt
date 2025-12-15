package com.rhannagan.omadaimageloadingapp.ui.feature.home

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.rhannagan.omadaimageloadingapp.R
import com.rhannagan.omadaimageloadingapp.domain.model.Photo
import com.rhannagan.omadaimageloadingapp.ui.components.OmadaSearchBar
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    homeScreenViewModel: HomeScreenViewModel,
    sharedViewModel: SharedViewModel,
    onImageClicked: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val snackbarScope = rememberCoroutineScope()
    val listState = rememberLazyGridState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            OmadaSearchBar(
                textFieldState = rememberTextFieldState(),
                onSearch = { search ->
                    homeScreenViewModel.onSearchRequested(search)
                },
                searchResults = emptyList()
            )
        },
        contentWindowInsets = WindowInsets.safeDrawing,
    ) { paddingValues ->
        val uiState = homeScreenViewModel.uiState.collectAsState()
        val lastItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
        val cachedPhotos = remember { mutableStateOf<List<Photo>>(listOf())}

        LaunchedEffect(listState.firstVisibleItemIndex) {
            if (lastItem == cachedPhotos.value.size - 1) {
                homeScreenViewModel.onLoadMore()
            }
        }

        when(uiState.value) {
            is HomeScreenViewModel.UiState.Loading -> {

                val loadingState = uiState.value as HomeScreenViewModel.UiState.Loading

                if (loadingState.isLoadingList) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.width(64.dp),
                            color = MaterialTheme.colorScheme.secondary,
                            trackColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    }
                }
            }

            is HomeScreenViewModel.UiState.Loaded -> {
                val loadedState = uiState.value as HomeScreenViewModel.UiState.Loaded
                cachedPhotos.value = loadedState.photos

                if (loadedState.photos.isEmpty()) {
                    val actionLabel = stringResource(R.string.close)
                    val message = stringResource(R.string.no_results_found)
                    LaunchedEffect(uiState) {
                        snackbarScope.launch {
                            snackbarHostState.showSnackbar(
                                message = message,
                                actionLabel = actionLabel
                            )
                        }
                    }
                }
            }

            is HomeScreenViewModel.UiState.Error -> {
                val message = stringResource(R.string.something_went_wrong)
                val actionLabel = stringResource(R.string.close)
                LaunchedEffect(uiState) {
                    snackbarScope.launch {
                        snackbarHostState.showSnackbar(
                            message = message,
                            actionLabel = actionLabel
                        )
                    }
                }
            }
        }

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            LazyVerticalGrid(
                state = listState,
                columns = GridCells.Fixed(3),
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(cachedPhotos.value) { photo ->
                    Box(
                        modifier = Modifier
                            .size(120.dp)
                            .padding(8.dp)
                    ) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(photo.imageUrl)
                                .crossfade(true)
                                .build(),
                            contentDescription = photo.title,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxSize()
                                .clickable {
                                    sharedViewModel.setResult(photo)
                                    onImageClicked.invoke()
                                }
                        )
                    }
                }

                if (uiState.value is HomeScreenViewModel.UiState.Loading &&
                    (uiState.value as HomeScreenViewModel.UiState.Loading).isLoadingMore
                ) {
                    item(
                        span = { GridItemSpan(maxLineSpan) }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.width(64.dp),
                                color = MaterialTheme.colorScheme.secondary,
                                trackColor = MaterialTheme.colorScheme.surfaceVariant
                            )
                        }
                    }
                }
            }
        }
    }
}