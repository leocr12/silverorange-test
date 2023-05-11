package com.silverorange.videoplayer.domain

import com.silverorange.videoplayer.data.ApiService
import com.silverorange.videoplayer.data.model.Video
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class VideoRepositoryImpl @Inject constructor(private val apiService: ApiService): VideoRepository {
    override suspend fun getVideos(): List<Video> {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        return apiService.getVideos().sortedByDescending {
            val date = dateFormat.parse(it.publishedAt)
            date?.time
        }
    }
}