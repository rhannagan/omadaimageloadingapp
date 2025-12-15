package com.rhannagan.omadaimageloadingapp.ui.feature.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rhannagan.omadaimageloadingapp.domain.model.Photo
import com.rhannagan.omadaimageloadingapp.domain.usecase.GetRecentPhotosUseCase
import com.rhannagan.omadaimageloadingapp.domain.usecase.SearchPhotosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val getRecentPhotosUseCase: GetRecentPhotosUseCase,
    private val searchPhotosUseCase: SearchPhotosUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState>(
        UiState.Loading(isLoadingList = true, isLoadingMore = false)
    )
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private var lastSearch: String = ""
    private var currentPage = 1

    init {
        fetchPhotos(shouldResetList = true)
    }

    fun onSearchRequested(search: String) {
        lastSearch = search
        fetchPhotos(shouldResetList = true)
    }

    fun onLoadMore() {
        val lastPhoto = (_uiState.value as? UiState.Loaded)?.photos?.lastOrNull() ?: return

        if (lastPhoto.currentPage < lastPhoto.totalPages) {
            fetchPhotos(currentPage + 1, shouldResetList = false)
        } else {
            _uiState.value = UiState.Loading(isLoadingList = false, isLoadingMore = false)
        }
    }

    private fun fetchPhotos(page: Int = 1, shouldResetList: Boolean) {
        viewModelScope.launch {
            val currentPhotos = (_uiState.value as? UiState.Loaded)?.photos ?: emptyList()
            _uiState.value = UiState.Loading(
                isLoadingList = shouldResetList,
                isLoadingMore = !shouldResetList
            )

            try {
                val newPhotos = if (lastSearch.isBlank())
                    getRecentPhotosUseCase.getRecentPhotos(page)
                    else
                    searchPhotosUseCase.searchPhotos(lastSearch, page)

                if (shouldResetList) {
                    _uiState.value = UiState.Loaded(newPhotos)
                } else {
                    _uiState.value = UiState.Loaded(currentPhotos + newPhotos)
                }
            } catch (ex: Exception) {
                Log.e(
                    this.javaClass.simpleName,
                    "Encountered error while fetching photos",
                    ex
                )
                _uiState.value = UiState.Error
            }
        }
    }

    sealed class UiState {
        data class Loading(val isLoadingList: Boolean, val isLoadingMore: Boolean) : UiState()
        data class Loaded(val photos: List<Photo>) : UiState()
        object Error : UiState()
    }
}