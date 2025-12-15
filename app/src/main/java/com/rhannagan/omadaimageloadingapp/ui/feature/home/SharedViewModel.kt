package com.rhannagan.omadaimageloadingapp.ui.feature.home

import androidx.lifecycle.ViewModel
import com.rhannagan.omadaimageloadingapp.domain.model.Photo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor() : ViewModel() {
    private val _selectedPhoto = MutableStateFlow(Photo.EMPTY)
    val selectedPhoto = _selectedPhoto.asStateFlow()

    fun setResult(photo: Photo) {
        _selectedPhoto.value = photo
    }
}