package com.silverorange.videoplayer.feature.videolist

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory
import com.google.android.exoplayer2.ui.PlayerView
import com.silverorange.videoplayer.domain.VideoListState

@Composable
fun VideoList(modifier: Modifier = Modifier,
              state: VideoListState
) {
    when (state) {
        is VideoListState.Loading -> {

        }
        is VideoListState.Success -> {
            val mediaSources = state.videoList.map {
                val mediaItem = MediaItem.fromUri(it.fullURL)
                DefaultMediaSourceFactory(LocalContext.current).createMediaSource(mediaItem)
            }

            val player = rememberExoPlayer()

            LaunchedEffect(mediaSources) {
                player.setMediaSources(mediaSources)
                player.prepare()
            }

            AndroidView(factory = { context ->
                PlayerView(context).apply {
                    this.player = player

                }
            }, modifier = Modifier.fillMaxWidth())
        }
        is VideoListState.Error -> {

        }
    }
}

@Composable
fun rememberExoPlayer(): ExoPlayer {
    val context = LocalContext.current
    val player = remember {
        ExoPlayer.Builder(context).build()
    }

    DisposableEffect(Unit) {
        onDispose {
            player.release()
        }
    }

    return player
}