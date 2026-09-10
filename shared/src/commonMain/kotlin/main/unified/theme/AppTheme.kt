package main.unified.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import org.jetbrains.compose.resources.Font


/**
 * 官方推荐的 Theme 包装函数
 */
@Composable
fun UnifiedTheme(
    darkTheme: Boolean = false,
    icons: AppIcons = AppIcons(),
    font: AppFonts = AppFonts(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) darkColorScheme() else lightColorScheme()

    // 通过 FontFamily(Font(font.regularFont)) 将 FontResource 加载并转换成了 FontFamily
    val regularFontFamily = FontFamily(Font(font.regularFont))

    val typography = remember(regularFontFamily) {
        Typography(
            bodyLarge = TextStyle(fontFamily = regularFontFamily),
        )
    }

    CompositionLocalProvider(
        LocalAppIcons provides icons,
        LocalAppFonts provides font,
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            content = content,
            typography = typography
        )
    }
}