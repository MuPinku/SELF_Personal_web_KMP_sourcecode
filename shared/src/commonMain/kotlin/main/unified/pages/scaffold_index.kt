package main.unified.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import org.jetbrains.compose.resources.painterResource
import main.unified.resources.Res
import main.unified.resources.background_test
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeSource
import main.unified.components.FloatingGlassSidebar
import main.unified.components.MarkdownArticle

@Composable
fun AppScaffold() {
    val hazeState = remember { HazeState() }
    var selectedIndex by remember { mutableStateOf(0) }

    // 全局最外层容器
    Box(
        modifier = Modifier
            .fillMaxSize()
            // 基础暗色夜空背景
            .background(Color(0xFF0B0D13))
    ) {
        // =========================================================================
        // 🖼️ 全局背景图：作为 hazeSource，玻璃组件（侧边栏等）会实时模糊折射这张图。
        // 上面覆盖一层暗色渐变，保证前景文字可读性，同时保留玻璃拟态的高对比度底图。
        // =========================================================================
        Box(
            modifier = Modifier
                .fillMaxSize()
                .hazeSource(state = hazeState)
        ) {
            Image(
                painter = painterResource(Res.drawable.background_test),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            // 暗色遮罩：压暗图片，保证文字与玻璃卡片的对比度
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFF0B0D13).copy(alpha = 0.45f),
                                Color(0xFF0B0D13).copy(alpha = 0.65f)
                            )
                        )
                    )
            )
        }

        // =========================================================================
        // 🚀 悬浮布局：侧边栏不再顶天立地，而是悬浮胶囊岛（Floating Capsule Island）
        // =========================================================================
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp) // 四周留出间隙，产生悬浮漂浮感
        ) {
            // 浮动玻璃拟态侧边栏
            FloatingGlassSidebar(
                hazeState = hazeState,
                selectedIndex = selectedIndex,
                onNavigate = { selectedIndex = it },
                modifier = Modifier
                    .width(72.dp)
                    .fillMaxHeight()
            )

            Spacer(modifier = Modifier.width(16.dp))

            // 主内容区（同样带一点微弱玻璃悬浮感）
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                MainContent(selectedIndex = selectedIndex)
            }
        }
    }
}



/**
 * 右侧主内容卡片（配套浮动半透明玻璃质感）
 */
@Composable
private fun MainContent(selectedIndex: Int) {
    val titles = listOf("首页内容看板", "全局搜索中心", "系统配置中心")
    val contentShape = RoundedCornerShape(24.dp)
    if (selectedIndex == 1){
        blog_page()
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .shadow(
                elevation = 16.dp,
                shape = contentShape,
                ambientColor = Color.Black.copy(alpha = 0.5f)
            )
            .clip(contentShape)
            // 磨砂半透明卡片
            .background(Color(0xFF131722).copy(alpha = 0.4f))
            .border(
                width = 1.dp,
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.White.copy(alpha = 0.2f),
                        Color.White.copy(alpha = 0.05f)
                    )
                ),
                shape = contentShape
            )
            .padding(32.dp)
    ) {
        Text(
            text = titles.getOrElse(selectedIndex) { "页面" },
            color = Color(0xFFF8FAFC),
            fontSize = 24.sp
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "观察左侧侧边栏：后方有炫彩霓虹光斑穿透，并被 Haze 实时雾化折射，呈现出立体的玻璃拟态（Glassmorphism）效果。",
            color = Color(0xFF94A3B8),
            fontSize = 14.sp,
            lineHeight = 22.sp
        )
    }
}

@Composable
fun test_blog_makedown(){
    val content : String= """
    # Hello Markdown

            This is a simple markdown example with:

    - Bullet points
            - **Bold text**
            - *Italic text*

            [Check out this link](https://github.com/mikepenz/multiplatform-markdown-renderer)
    """
    MarkdownArticle(
        content = content,
        modifier = Modifier.fillMaxSize(),
    )
}
