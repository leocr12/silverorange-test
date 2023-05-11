package com.silverorange.videoplayer.domain

import com.silverorange.videoplayer.data.model.Video

interface VideoRepository {
    suspend fun getVideos(): List<Video>
}