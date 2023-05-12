package com.silverorange.videoplayer.mocks

import com.silverorange.videoplayer.data.model.Author
import com.silverorange.videoplayer.data.model.Video

fun getMockedSortedVideos(): List<Video> {
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

    return listOf(video3, video2, video1)
}