package com.rhannagan.omadaimageloadingapp.domain.repository

import com.rhannagan.omadaimageloadingapp.data.FlickrService
import com.rhannagan.omadaimageloadingapp.domain.model.FlickrResponse
import jakarta.inject.Inject

class FlickrRepository @Inject constructor(
    private val flickrService: FlickrService
) {
    suspend fun getRecentPhotos(page: Int = 1): FlickrResponse =
        flickrService.getRecentPhotos(page = page)

    suspend fun searchPhotos(searchText: String, page: Int = 1): FlickrResponse =
        flickrService.searchPhotos(searchText = searchText, page = page)
}