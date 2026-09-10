package main.unified.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import main.unified.resources.NotoSansSC_Regular
import main.unified.resources.Res
import org.jetbrains.compose.resources.FontResource

@Immutable
data class AppFonts(
    val regularFont: FontResource = Res.font.NotoSansSC_Regular,
)

val LocalAppFonts = staticCompositionLocalOf { AppFonts() }