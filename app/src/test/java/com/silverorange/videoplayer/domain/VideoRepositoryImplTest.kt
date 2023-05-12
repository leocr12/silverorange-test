package com.silverorange.videoplayer.domain

import com.silverorange.videoplayer.data.ApiService
import com.silverorange.videoplayer.data.model.Author
import com.silverorange.videoplayer.data.model.Video
import com.silverorange.videoplayer.mocks.getMockedSortedVideos
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class VideoRepositoryImplTest {

    @Mock
    private lateinit var apiService: ApiService

    private lateinit var videoRepository: VideoRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        videoRepository = VideoRepositoryImpl(apiService)
    }

    @Test
    fun `getVideos should return videos sorted by descending published date`() = runBlocking {
        // Arrange
        val author = Author("a1", "Author")
        val video1 = Video(
            id = "1",
            title = "Title 1",
            description = "Description 1",
            hlsURL = "hlsUrl",
            fullURL = "fullUrl",
            author = author,
            publishedAt = "2019-12-15T22:17:00.000Z",
        )
        val video2 = Video(
            id = "2",
            title = "Title 2",
            description = "Description 2",
            hlsURL = "hlsUrl",
            fullURL = "fullUrl",
            author = author,
            publishedAt = "2020-01-20T12:34:56.000Z",
        )
        val video3 = Video(
            id = "3",
            title = "Title 3",
            description = "Description 3",
            hlsURL = "hlsUrl",
            fullURL = "fullUrl",
            author = author,
            publishedAt = "2021-05-01T08:00:00.000Z",
        )
        val videos = listOf(video1, video2, video3)
        `when`(apiService.getVideos()).thenReturn(videos)

        // Act
        val sortedVideos = videoRepository.getVideos()

        // Assert
        assertEquals(sortedVideos[0], video3)
        assertEquals(sortedVideos[1], video2)
        assertEquals(sortedVideos[2], video1)
    }

    @Test
    fun `getVideos returns non-empty list of videos when API service call is successful`() = runBlocking {
        val expectedVideos = getMockedSortedVideos()
        `when`(apiService.getVideos()).thenReturn(expectedVideos)

        // When
        val actualVideos = videoRepository.getVideos()

        // Then
        assertTrue(actualVideos.isNotEmpty())
        assertEquals(actualVideos, expectedVideos)
    }
}
