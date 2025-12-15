package com.rhannagan.omadaimageloadingapp.data

import com.rhannagan.omadaimageloadingapp.domain.model.FlickrResponse
import com.rhannagan.omadaimageloadingapp.domain.model.PhotoDetails
import com.rhannagan.omadaimageloadingapp.domain.model.Photos
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test

class FlickrServiceTest {

    private val service: FlickrService = mockk(relaxed = true)
    private val defaultPhotoDetails = PhotoDetails(
        id,
        owner,
        secret,
        server,
        farm,
        title,
        isPublic,
        isFriend,
        isFamily
    )
    private val defaultFlickrResponse = FlickrResponse(
        photos = Photos(
            page = 1,
            pages = 10,
            perpage = 10,
            total = 40,
            photo = listOf(defaultPhotoDetails)
        )
    )

    @Test
    fun `getRecentPhotos returns results from the first page`() = runTest {
        coEvery { service.getRecentPhotos() } returns defaultFlickrResponse

        val result = service.getRecentPhotos()

        assertEquals(result.photos.photo, listOf(defaultPhotoDetails))
        assertEquals(result.photos.page, 1)
    }

    @Test
    fun `when searching for photos, a result is returned`() = runTest {
        coEvery { service.searchPhotos(searchText = "mountains") } returns defaultFlickrResponse

        val result = service.searchPhotos(searchText = "mountains")

        assertEquals(result.photos.photo, listOf(defaultPhotoDetails))
        assertEquals(result.photos.page, 1)
    }

    @Test
    fun `when providing a page number, that page of photos is returned`() = runTest {
        val response = FlickrResponse(
            photos = defaultFlickrResponse.photos.copy(page = 13)
        )
        coEvery { service.getRecentPhotos(page = 13) } returns response

        val result = service.getRecentPhotos(page = 13)

        assertEquals(result.photos.page, 13)
    }

    @Test
    fun `when providing a page number during a search, that page of photos is returned`() = runTest {
        val response = FlickrResponse(
            photos = defaultFlickrResponse.photos.copy(page = 13)
        )
        coEvery { service.searchPhotos(searchText = "mountains", page = 13) } returns response

        val result = service.searchPhotos(searchText = "mountains", page = 13)

        assertEquals(result.photos.page, 13)
    }

    companion object {
        const val id = "id"
        const val owner = "owner"
        const val secret = "secret"
        const val server = "server"
        const val farm = 32
        const val isPublic = 0
        const val isFriend = 0
        const val isFamily = 0
        const val title = "title"
    }
}