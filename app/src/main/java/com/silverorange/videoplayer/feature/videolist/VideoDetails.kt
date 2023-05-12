package com.silverorange.videoplayer.feature.videolist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.silverorange.videoplayer.data.model.Video
import dev.jeziellago.compose.markdowntext.MarkdownText

@Composable
fun VideoDetails(modifier: Modifier = Modifier, video: Video) {
    Column(modifier = modifier.fillMaxWidth().padding(16.dp)) {
        MarkdownText(markdown = video.title)
        MarkdownText(markdown = video.author.name, color = Color.LightGray)
        MarkdownText(markdown = video.description, modifier = Modifier.padding(top = 8.dp))
    }
}