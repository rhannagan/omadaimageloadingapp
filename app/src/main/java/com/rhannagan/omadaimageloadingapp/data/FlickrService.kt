package com.rhannagan.omadaimageloadingapp.data

import com.rhannagan.omadaimageloadingapp.domain.model.FlickrResponse
import com.rhannagan.omadaimageloadingapp.domain.model.PhotoDetails
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickrService {

    @GET("/services/rest")
    suspend fun getRecentPhotos(
        @Query("method") method: String = "flickr.photos.getRecent",
        @Query("api_key") apiKey: String = "a0222db495999c951dc33702500fdc4d",
        @Query("format") format: String = "json",
        @Query("nojsoncallback") nojsoncallback: Int = 1,
        @Query("page") page: Int = 1
    ): FlickrResponse

    @GET("/services/rest")
    suspend fun searchPhotos(
        @Query("method") method: String = "flickr.photos.search",
        @Query("api_key") apiKey: String = "a0222db495999c951dc33702500fdc4d",
        @Query("text") searchText: String,
        @Query("format") format: String = "json",
        @Query("nojsoncallback") nojsoncallback: Int = 1,
        @Query("page") page: Int = 1
    ): FlickrResponse


    companion object {
        const val BASE_URL = "https://www.flickr.com/services/rest/?method=flickr.photos.getRecent&api_key=a0222db495999c951dc33702500fdc4d&format=json&nojsoncallback=1"

    }
}