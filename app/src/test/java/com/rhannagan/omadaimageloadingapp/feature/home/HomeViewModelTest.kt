package com.rhannagan.omadaimageloadingapp.feature.home

import com.rhannagan.omadaimageloadingapp.domain.model.Photo
import com.rhannagan.omadaimageloadingapp.domain.usecase.GetRecentPhotosUseCase
import com.rhannagan.omadaimageloadingapp.domain.usecase.SearchPhotosUseCase
import com.rhannagan.omadaimageloadingapp.ui.feature.home.HomeScreenViewModel
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    private val photo1 = Photo(
        IMAGE_URL,
        ID,
        owner,
        secret,
        server,
        title,
        totalPages,
        currentPage
    )

    private val getRecentPhotosUseCase: GetRecentPhotosUseCase = mockk {
         coEvery { getRecentPhotos() } returns listOf(photo1)
    }
    private val searchPhotosUseCase: SearchPhotosUseCase = mockk {
        coEvery { searchPhotos(anyString()) } returns listOf(photo1)
    }
    private lateinit var target: HomeScreenViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        target = HomeScreenViewModel(
            getRecentPhotosUseCase,
            searchPhotosUseCase
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `on init, recent photos are fetched, enters Loaded state`() = runTest() {
        target = HomeScreenViewModel(
            getRecentPhotosUseCase,
            searchPhotosUseCase
        )

        assertEquals(
            HomeScreenViewModel.UiState.Loading(isLoadingList = true, isLoadingMore = false),
            target.uiState.value
        )

        advanceUntilIdle()

        assertEquals(
            HomeScreenViewModel.UiState.Loaded(listOf(photo1)),
            target.uiState.value
        )
    }

    @Test
    fun `when searched photos have no results, the state still shows Loaded`() = runTest() {

        coEvery { searchPhotosUseCase.searchPhotos("candy") } returns emptyList()

        target = HomeScreenViewModel(
            getRecentPhotosUseCase,
            searchPhotosUseCase
        )

        advanceUntilIdle()

        target.onSearchRequested("candy")

        advanceUntilIdle()
        testScheduler.advanceUntilIdle()

        assertEquals(
            HomeScreenViewModel.UiState.Loaded(emptyList()),
            target.uiState.value
        )
    }

    companion object {
        const val IMAGE_URL = "https://live.staticflickr.com/65535/54983538477_34fb77bc9f.jpg"
        const val ID = "id"
        const val owner = "owner"
        const val secret = "secret"
        const val server = "server"
        const val title = "title"
        const val totalPages = 20
        const val currentPage = 5
    }
}