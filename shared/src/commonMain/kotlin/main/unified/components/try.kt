package main.unified.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.HazeTint
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.materials.HazeMaterials
import main.unified.resources.Res
import main.unified.resources.home
import main.unified.theme.LocalAppIcons
import org.jetbrains.compose.resources.painterResource

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
        // 🔮 关键解法：为什么之前看不出玻璃拟态？
        // 因为纯色/简单渐变被模糊后依然像纯色！玻璃拟态必须有【穿透的高对比度光斑/图元】。
        // 下面这层作为 hazeSource，专门在左上角（侧边栏正后方）布置了炫彩环境光球（Ambient Orbs）。
        // =========================================================================
        Box(
            modifier = Modifier
                .fillMaxSize()
                .hazeSource(state = hazeState)
        ) {
            // 1号光斑：紫粉色霓虹球（恰好穿过侧边栏后方，形成强烈的磨砂折射）
            Box(
                modifier = Modifier
                    .offset(x = (-30).dp, y = 60.dp)
                    .size(240.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.radialGradient(
                            colors = listOf(
                                Color(0xFFEC4899).copy(alpha = 0.65f), // 荧光玫红
                                Color(0xFF8B5CF6).copy(alpha = 0.35f), // 梦幻紫
                                Color.Transparent
                            )
                        )
                    )
                    .blur(60.dp) // 光斑自身大范围漫反射
            )

            // 2号光斑：深海青蓝球（在中下方投射，形成冷暖交织）
            Box(
                modifier = Modifier
                    .offset(x = 10.dp, y = 360.dp)
                    .size(260.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.radialGradient(
                            colors = listOf(
                                Color(0xFF06B6D4).copy(alpha = 0.55f), // 荧光青
                                Color(0xFF3B82F6).copy(alpha = 0.25f), // 湛蓝
                                Color.Transparent
                            )
                        )
                    )
                    .blur(70.dp)
            )

            // 3号光斑：右上角主内容背景微光
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = 80.dp, y = (-50).dp)
                    .size(360.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.radialGradient(
                            colors = listOf(
                                Color(0xFF6366F1).copy(alpha = 0.25f),
                                Color.Transparent
                            )
                        )
                    )
                    .blur(90.dp)
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
 * 核心：浮动玻璃侧边栏组件
 */
@Composable
fun FloatingGlassSidebar(
    hazeState: HazeState,
    selectedIndex: Int = 0,
    modifier: Modifier = Modifier,
    onNavigate: (Int) -> Unit = {}
) {
    // 四角完全独立大圆角（胶囊悬浮造型）
    val floatingShape = RoundedCornerShape(26.dp)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            // 1. 浮动感第一要素：深层投射柔和阴影，将边框与背景彻底拉开高度（Z轴高度）
            .shadow(
                elevation = 20.dp,
                shape = floatingShape,
                ambientColor = Color.Black.copy(alpha = 0.7f),
                spotColor = Color(0xFF6366F1).copy(alpha = 0.35f) // 微微带一点主题蓝紫色的环境光晕
            )
            // 2. 裁切形状
            .clip(floatingShape)
            // 3. 毛玻璃材质：Haze 采样模糊
            .hazeEffect(
                state = hazeState,
                style = HazeMaterials.ultraThin()
            ) {
                blurRadius = 32.dp // 加大模糊半径，让光斑化为柔和雾气
                style = HazeStyle(
                    blurRadius = 32.dp,
                    tints = listOf(
                        // 极轻微的乳白色提亮，模拟物理玻璃表面的微反光
                        HazeTint(Color.White.copy(alpha = 0.08f))
                    )
                )
            }
            // 4. 双层底色保底：半透明深灰，确保在任何 Web 渲染后端下都晶莹透亮
            .background(Color(0xFF131722).copy(alpha = 0.35f))
            // 5. 浮动感第二要素：拟物高光反光边框（物理玻璃边缘的棱镜内反射）
            .border(
                width = 1.2.dp,
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.White.copy(alpha = 0.45f), // 顶部强受光面（最亮）
                        Color.White.copy(alpha = 0.12f), // 中段半透
                        Color.White.copy(alpha = 0.04f), // 阴影过渡
                        Color.White.copy(alpha = 0.20f)  // 底部微弱地面反光
                    )
                ),
                shape = floatingShape
            )
            .padding(vertical = 20.dp)
    ) {
        // 顶部 Logo 标识（带光泽感）
        Box(
            modifier = Modifier
                .size(42.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(
                    Brush.linearGradient(
                        colors = listOf(Color(0xFF8B5CF6), Color(0xFFEC4899))
                    )
                )
                .border(
                    width = 1.dp,
                    color = Color.White.copy(alpha = 0.35f),
                    shape = RoundedCornerShape(14.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "U",
                color = Color.White,
                fontSize = 18.sp,
                style = MaterialTheme.typography.titleMedium
            )
        }

        // 中部导航 Item 组
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            SidebarIcon(
                icon = painterResource(Res.drawable.home),
                label = "首页",
                isSelected = selectedIndex == 0,
                onClick = { onNavigate(0) }
            )

            SidebarIcon(
                icon = painterResource(LocalAppIcons.current.search),
                label = "搜索",
                isSelected = selectedIndex == 1,
                onClick = { onNavigate(1) }
            )

            SidebarIcon(
                icon = painterResource(LocalAppIcons.current.setting),
                label = "设置",
                isSelected = selectedIndex == 2,
                onClick = { onNavigate(2) }
            )
        }

        // 底部微留白维持空间平衡
        Spacer(modifier = Modifier.size(42.dp))
    }
}

/**
 * 导航按钮组件
 */
@Composable
private fun SidebarIcon(
    icon: Painter,
    label: String,
    isSelected: Boolean = false,
    onClick: () -> Unit = {}
) {
    val interactionSource = remember { MutableInteractionSource() }

    // 状态配色：
    // - 选中：晶莹微透高亮底色 + 高光彩色边框 + 荧光紫白图标
    // - 未选中：纯净透明 + 低饱和柔和灰
    val activeBg = if (isSelected) Color.White.copy(alpha = 0.15f) else Color.Transparent
    val activeBorderColor = if (isSelected) Color.White.copy(alpha = 0.25f) else Color.Transparent
    val contentColor = if (isSelected) Color(0xFFF8FAFC) else Color(0xFF94A3B8)
    val iconTint = if (isSelected) Color(0xFFA5B4FC) else Color(0xFF94A3B8)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(58.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(activeBg)
            .border(
                width = 1.dp,
                color = activeBorderColor,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            )
            .padding(vertical = 8.dp, horizontal = 4.dp)
    ) {
        Image(
            painter = icon,
            contentDescription = label,
            modifier = Modifier.size(22.dp),
            colorFilter = ColorFilter.tint(iconTint)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            fontSize = 10.sp,
            color = contentColor
        )
    }
}

/**
 * 右侧主内容卡片（配套浮动半透明玻璃质感）
 */
@Composable
private fun MainContent(selectedIndex: Int) {
    val titles = listOf("首页内容看板", "全局搜索中心", "系统配置中心")
    val contentShape = RoundedCornerShape(24.dp)

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