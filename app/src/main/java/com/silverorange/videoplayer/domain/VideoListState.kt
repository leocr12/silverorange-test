package com.silverorange.videoplayer.domain

import com.silverorange.videoplayer.data.model.Video

sealed class VideoListState {
    object Loading: VideoListState()
    data class Success(val videoList: List<Video>): VideoListState()
    data class Error(val message: String): VideoListState()
}
