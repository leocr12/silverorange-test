package com.silverorange.videoplayer.feature.videolist

import android.view.View
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.Player.MEDIA_ITEM_TRANSITION_REASON_SEEK
import com.google.android.exoplayer2.Player.REPEAT_MODE_ONE
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory
import com.google.android.exoplayer2.ui.PlayerView
import com.silverorange.videoplayer.domain.VideoListState
import kotlinx.coroutines.delay

@Composable
fun VideoList(
    modifier: Modifier = Modifier,
    state: VideoListState
) {
    val transitionIdState = remember { mutableStateOf("") }
    val shouldShowControllers = remember { mutableStateOf(true) }

    when (state) {
        is VideoListState.Loading -> {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
        is VideoListState.Success -> {
            if (transitionIdState.value.isEmpty()) {
                transitionIdState.value = state.videoList.firstOrNull()?.id ?: ""
            }
            val video = state.videoList.find { it.id == transitionIdState.value }
            val mediaSources = state.videoList.map {
                val mediaItem = MediaItem.Builder().setMediaId(it.id).setUri(it.hlsURL).build()
                DefaultMediaSourceFactory(LocalContext.current).createMediaSource(mediaItem)
            }

            val player = rememberExoPlayer(transitionIdState)
            val isPaused = remember { mutableStateOf(true) }

            LaunchedEffect(mediaSources) {
                player.setMediaSources(mediaSources)
                player.repeatMode = REPEAT_MODE_ONE
                player.prepare()
            }

            LaunchedEffect(isPaused) {
                delay(3000)
                shouldShowControllers.value = false
            }

            ConstraintLayout(modifier = Modifier.fillMaxSize()) {
                val (videoPlayer, details, controllers) = createRefs()

                Box(modifier = Modifier
                    .clickable {
                        shouldShowControllers.value = !shouldShowControllers.value
                    }
                    .constrainAs(videoPlayer) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        height = Dimension.wrapContent
                    }) {
                    AndroidView(
                        factory = { context ->
                            PlayerView(context).apply {
                                this.player = player
                                useController = false
                            }
                        }, modifier = Modifier
                            .fillMaxWidth()
                    )
                }

                if (shouldShowControllers.value) {
                    VideoControllers(modifier = Modifier
                        .fillMaxSize()
                        .constrainAs(controllers) {
                            start.linkTo(parent.start)
                            top.linkTo(videoPlayer.top)
                            end.linkTo(parent.end)
                            bottom.linkTo(videoPlayer.bottom)
                            height = Dimension.fillToConstraints
                        },
                        isPaused = isPaused,
                        onPreviousClick = {
                            player.seekToPrevious()
                        }, onNextClick = {
                            player.seekToNext()
                        }, onPlayPauseClick = {
                            if (player.playWhenReady) {
                                player.pause()
                                isPaused.value = true
                            } else {
                                player.play()
                                isPaused.value = false
                            }
                        })
                }

                video?.let {
                    VideoDetails(video = video, modifier = Modifier.constrainAs(details) {
                        start.linkTo(parent.start)
                        top.linkTo(videoPlayer.bottom)
                        end.linkTo(parent.end)
                    })
                }
            }
        }
        is VideoListState.Error -> {
            Text(text = "Sorry, something went wrong")
        }
    }
}

@Composable
fun rememberExoPlayer(transitionIdState: MutableState<String>): ExoPlayer {
    val context = LocalContext.current
    val player = remember {
        ExoPlayer.Builder(context).build()
    }

    val playerListener = object : Player.Listener {
        override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
            super.onMediaItemTransition(mediaItem, reason)
            if (reason == MEDIA_ITEM_TRANSITION_REASON_SEEK) {
                transitionIdState.value = mediaItem?.mediaId ?: ""
            }
        }
    }

    DisposableEffect(Unit) {
        player.addListener(playerListener)
        onDispose {
            player.removeListener(playerListener)
            player.release()
        }
    }

    return player
}