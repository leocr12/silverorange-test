package com.silverorange.videoplayer.feature.videolist

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.silverorange.videoplayer.domain.VideoListViewModel

@Composable
fun VideoListScreen(viewModel: VideoListViewModel) {
    val state by remember { viewModel.state }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Video Player") })
        },
        content = { contentPadding ->
            VideoList(modifier = Modifier.padding(contentPadding), state = state)
        }
    )
}