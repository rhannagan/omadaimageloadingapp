package com.rhannagan.omadaimageloadingapp.repository

import com.rhannagan.omadaimageloadingapp.data.FlickrService
import com.rhannagan.omadaimageloadingapp.data.FlickrServiceTest.Companion.farm
import com.rhannagan.omadaimageloadingapp.data.FlickrServiceTest.Companion.id
import com.rhannagan.omadaimageloadingapp.data.FlickrServiceTest.Companion.isFamily
import com.rhannagan.omadaimageloadingapp.data.FlickrServiceTest.Companion.isFriend
import com.rhannagan.omadaimageloadingapp.data.FlickrServiceTest.Companion.isPublic
import com.rhannagan.omadaimageloadingapp.data.FlickrServiceTest.Companion.owner
import com.rhannagan.omadaimageloadingapp.data.FlickrServiceTest.Companion.secret
import com.rhannagan.omadaimageloadingapp.data.FlickrServiceTest.Companion.server
import com.rhannagan.omadaimageloadingapp.data.FlickrServiceTest.Companion.title
import com.rhannagan.omadaimageloadingapp.domain.model.FlickrResponse
import com.rhannagan.omadaimageloadingapp.domain.model.PhotoDetails
import com.rhannagan.omadaimageloadingapp.domain.model.Photos
import com.rhannagan.omadaimageloadingapp.domain.repository.FlickrRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test

class FlickrRepositoryTest {

    private val flickrService: FlickrService = mockk(relaxed = true)

    private val target = FlickrRepository(flickrService)

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
    fun `when getting recent photos, a FlickrResponse is returned`() = runTest {
        coEvery { flickrService.getRecentPhotos() } returns defaultFlickrResponse
        val result = target.getRecentPhotos()

        coVerify { flickrService.getRecentPhotos() }
        assertEquals(result, defaultFlickrResponse)
    }

    @Test
    fun `when searching for photos, a FlickrResponse is returned`() = runTest {
        val search = "puppies"
        coEvery { flickrService.searchPhotos(searchText = search) } returns defaultFlickrResponse
        val result = target.searchPhotos(searchText = search)

        coVerify { flickrService.searchPhotos(searchText = search) }
        assertEquals(result, defaultFlickrResponse)
    }

    @Test
    fun `when getting recent photos with a page number, that page of photos is returned`() = runTest {
        val response = FlickrResponse(
            photos = defaultFlickrResponse.photos.copy(page = 13)
        )
        coEvery { flickrService.getRecentPhotos(page = 13) } returns response
        val result = target.getRecentPhotos(page = 13)

        coVerify { flickrService.getRecentPhotos(page = 13) }
        assertEquals(result.photos.page, 13)
    }

    @Test
    fun `when searching photos with a page number, that page of photos is returned`() = runTest {
        val search = "puppies"
        val response = FlickrResponse(
            photos = defaultFlickrResponse.photos.copy(page = 13)
        )
        coEvery { flickrService.searchPhotos(searchText = search, page = 13) } returns response
        val result = target.searchPhotos(searchText = search,page = 13)

        coVerify { flickrService.searchPhotos(searchText = search, page = 13) }
        assertEquals(result.photos.page, 13)
    }
}