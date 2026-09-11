package main.unified.theme


import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.painter.Painter

// 导入 Compose 生成的资源对象（与 build.gradle.kts 中 packageOfResClass 保持一致）
import main.unified.resources.Res
import main.unified.resources.article
import main.unified.resources.favorites_icon
import main.unified.resources.home
import main.unified.resources.search
import main.unified.resources.setting
import main.unified.resources.tag
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

/**
 * 集中管理应用图标数据类
 */
@Immutable
data class AppIcons(
    val favorite: DrawableResource = Res.drawable.favorites_icon,
    val home: DrawableResource = Res.drawable.home,
    val article: DrawableResource = Res.drawable.article,
    val search: DrawableResource = Res.drawable.search,
    val tag: DrawableResource = Res.drawable.tag,
    val setting: DrawableResource = Res.drawable.setting,
)


/**
 * 提供 LocalAppIcons 给组件树向下传递
 */
val LocalAppIcons = staticCompositionLocalOf { AppIcons() }

@Composable
fun DrawableResource.asIconPainter(): Painter = painterResource(this)




