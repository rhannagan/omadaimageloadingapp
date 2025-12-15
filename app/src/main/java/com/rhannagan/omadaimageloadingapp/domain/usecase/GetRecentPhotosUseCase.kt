package com.rhannagan.omadaimageloadingapp.domain.usecase

import android.util.Log
import com.rhannagan.omadaimageloadingapp.domain.model.Photo
import com.rhannagan.omadaimageloadingapp.domain.repository.FlickrRepository
import jakarta.inject.Inject

class GetRecentPhotosUseCase @Inject constructor(
    private val flickrRepository: FlickrRepository
) {
    suspend fun getRecentPhotos(page: Int = 1): List<Photo> {

        val response = flickrRepository.getRecentPhotos(page)
        val totalPages = response.photos.pages
        val currentPage = response.photos.page

        return response.photos.photo.map { response ->
            Photo(
                imageUrl = "https://live.staticflickr.com/${response.server}/${response.id}_${response.secret}.jpg",
                id = response.id,
                owner = response.owner,
                secret = response.secret,
                server = response.server,
                title = response.title,
                totalPages = totalPages,
                currentPage = currentPage
            )
        }
    }
}