package com.rhannagan.omadaimageloadingapp.usecase

import com.rhannagan.omadaimageloadingapp.domain.model.FlickrResponse
import com.rhannagan.omadaimageloadingapp.domain.model.PhotoDetails
import com.rhannagan.omadaimageloadingapp.domain.model.Photos
import com.rhannagan.omadaimageloadingapp.domain.repository.FlickrRepository
import com.rhannagan.omadaimageloadingapp.domain.usecase.GetRecentPhotosUseCase
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class SearchPhotosUseCaseTest {

    private val photo1 = PhotoDetails(
        ID,
        OWNER,
        SECRET,
        SERVER,
        FARM,
        TITLE,
        IS_PUBLIC,
        IS_FRIEND,
        IS_FAMILY
    )
    private val photos: Photos = Photos(
        PAGE,
        PAGES,
        perpage,
        total,
        listOf(photo1)
    )
    private val flickrResponse = FlickrResponse(
        photos = photos,
        stat = STAT
    )

    private val flickrRepository: FlickrRepository = mockk {
        coEvery { getRecentPhotos() } returns flickrResponse
    }

    private lateinit var target: GetRecentPhotosUseCase

    @Before
    fun setup() {
        target = GetRecentPhotosUseCase(flickrRepository)
    }

    @Test
    fun `searchPhotosUseCase maps flickrResponse to list of Photo`() = runTest {
        val photo = target.getRecentPhotos().first()

        assertEquals(photo.id, ID)
        assertEquals(photo.owner, OWNER)
        assertEquals(photo.secret, SECRET)
        assertEquals(photo.server, SERVER)
        assertEquals(photo.title, TITLE)
        assertEquals(photo.totalPages, PAGES)
        assertEquals(photo.currentPage, PAGE)
    }

    @Test
    fun `searchPhotos will create the appropriate imageUrl for loading`() = runTest {
        val photo = target.getRecentPhotos().first()

        assertEquals(photo.imageUrl, IMAGE_URL)
    }

    companion object {
        private const val PAGE = 1
        private const val PAGES = 12
        private const val perpage = 20
        private const val total = 10000
        private const val ID = "id"
        private const val OWNER = "owner"
        private const val SECRET = "secret"
        private const val SERVER = "server"
        private const val FARM = 24
        private const val TITLE = "title"
        private const val IS_PUBLIC = 0
        private const val IS_FRIEND = 0
        private const val IS_FAMILY = 0
        private const val STAT = "ok"
        private const val IMAGE_URL = "https://live.staticflickr.com/server/id_secret.jpg"
    }

}