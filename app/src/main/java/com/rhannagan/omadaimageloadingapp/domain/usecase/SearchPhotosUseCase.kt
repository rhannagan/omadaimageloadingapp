package com.rhannagan.omadaimageloadingapp.domain.usecase

import android.util.Log
import com.rhannagan.omadaimageloadingapp.domain.model.Photo
import com.rhannagan.omadaimageloadingapp.domain.repository.FlickrRepository
import jakarta.inject.Inject

class SearchPhotosUseCase @Inject constructor(
    private val flickrRepository: FlickrRepository
) {
    suspend fun searchPhotos(searchText: String, page: Int = 1): List<Photo> {

        val searchedPhotos = flickrRepository.searchPhotos(searchText, page)
        val currentPage = searchedPhotos.photos.page
        val totalPages = searchedPhotos.photos.pages

        return searchedPhotos.photos.photo.map { response ->
            Photo(
                imageUrl = "https://live.staticflickr.com/${response.server}/${response.id}_${response.secret}.jpg",
                id = response.id,
                owner = response.owner,
                secret = response.secret,
                server = response.server,
                title = response.title,
                currentPage = currentPage,
                totalPages = totalPages
            )
        }
    }

}