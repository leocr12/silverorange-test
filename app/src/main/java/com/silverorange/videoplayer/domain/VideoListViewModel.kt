package com.silverorange.videoplayer.domain

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.silverorange.videoplayer.data.ApiException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideoListViewModel @Inject constructor(private val videoRepository: VideoRepository): ViewModel() {
    private val _state = mutableStateOf<VideoListState>(VideoListState.Loading)
    val state: State<VideoListState>
        get() = _state

    init {
        viewModelScope.launch {
            try {
                val videoList = videoRepository.getVideos()
                _state.value = VideoListState.Success(videoList)
            } catch (e: ApiException) {
                _state.value = VideoListState.Error(e.message ?: "Something went wrong :( Please try again later")
            }
        }
    }
}