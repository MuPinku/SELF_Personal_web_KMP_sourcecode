package main.unified.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.mikepenz.markdown.compose.Markdown
import com.mikepenz.markdown.compose.components.markdownComponents
import com.mikepenz.markdown.m3.Markdown
import com.mikepenz.markdown.m3.markdownColor
import com.mikepenz.markdown.model.DefaultMarkdownColors
import com.mikepenz.markdown.model.MarkdownColors
import com.mikepenz.markdown.model.MarkdownTypography
import com.mikepenz.markdown.utils.getUnescapedTextInNode

enum class ColorMode {
    Dark,
    Light
}

// MarkdownArticle.kt
@Composable
fun MarkdownArticle(
    content:String,
    modifier: Modifier = Modifier,
    Mode: ColorMode = ColorMode.Dark // TODO : ADD light mode
) {
    Box(modifier = modifier.fillMaxWidth()) {
        Markdown(
            colors = markdownColor(
                text = Color.White,
                codeBackground = Color.Black
            ),
            content = content.trimIndent()
        )
    }
}


