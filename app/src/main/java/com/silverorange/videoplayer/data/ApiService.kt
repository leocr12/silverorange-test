package com.silverorange.videoplayer.data

import com.silverorange.videoplayer.data.model.Video
import retrofit2.http.GET

interface ApiService {
    @GET("videos")
    suspend fun getVideos(): List<Video>
}