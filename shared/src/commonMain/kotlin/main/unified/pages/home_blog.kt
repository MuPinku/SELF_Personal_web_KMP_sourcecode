package main.unified.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import main.unified.backend.BlogMessage
import main.unified.backend.blog_search_test
import main.unified.components.MarkdownArticle



private val BlogCardShape = RoundedCornerShape(16.dp)

@Composable
fun blog_page() {
    // 模拟后端接口数据；后续替换为 Network 的真实请求即可
    val allBlogs = remember { blog_search_test() }
    var query by remember { mutableStateOf("") }
    var selectedBlog by remember { mutableStateOf<BlogMessage?>(null) }

    val filtered = remember(query, allBlogs) {
        if (query.isBlank()) allBlogs
        else allBlogs.filter {
            it.title.contains(query, ignoreCase = true) ||
                    it.briefContent.contains(query, ignoreCase = true)
        }
    }

    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        // ============ 左侧：标题 + 搜索 + 卡片列表（约 2/3 宽度） ============
        Column(
            modifier = Modifier
                .weight(2f)
                .fillMaxSize()
        ) {
            // ============ 顶部标题 + 搜索框 ============
            Text(
                text = "博客",
                color = Color(0xFFF8FAFC),
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))

            BlogSearchBar(
                query = query,
                onQueryChange = { query = it },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            // ============ 博客卡片列表 ============
            if (filtered.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "没有找到与 “$query” 相关的文章",
                        color = Color(0xFF64748B),
                        fontSize = 14.sp
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(filtered) { blog ->
                        BlogCard(
                            blog = blog,
                            onClick = { selectedBlog = blog }
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.width(20.dp))

        // ============ 右侧：信息面板（约 1/3 宽度） ============
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // TODO: 后续放日历 / 统计等小卡片，先占位
            BlogSideCard { }
        }
    }

    // 点击卡片进入文章详情（Markdown 渲染）
    selectedBlog?.let { blog ->
        BlogDetailOverlay(
            blog = blog,
            onClose = { selectedBlog = null }
        )
    }
}

/**
 * 右侧小卡片通用的玻璃拟态容器
 */
@Composable
private fun BlogSideCard(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    val sideShape = RoundedCornerShape(20.dp)
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(sideShape)
            .background(Color(0xFF131722).copy(alpha = 0.4f))
            .border(
                width = 1.dp,
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.White.copy(alpha = 0.15f),
                        Color.White.copy(alpha = 0.04f)
                    )
                ),
                shape = sideShape
            )
            .padding(20.dp),
        content = content
    )
}

/**
 * 顶部搜索栏（玻璃拟态胶囊输入框）
 */
@Composable
private fun BlogSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val searchShape = RoundedCornerShape(14.dp)
    Row(
        modifier = modifier
            .height(48.dp)
            .clip(searchShape)
            .background(Color(0xFF131722).copy(alpha = 0.5f))
            .border(
                width = 1.dp,
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.White.copy(alpha = 0.18f),
                        Color.White.copy(alpha = 0.05f)
                    )
                ),
                shape = searchShape
            )
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "🔍",
            fontSize = 15.sp
        )
        Spacer(modifier = Modifier.width(10.dp))

        Box(modifier = Modifier.weight(1f)) {
            if (query.isEmpty()) {
                Text(
                    text = "搜索文章标题或摘要…",
                    color = Color(0xFF64748B),
                    fontSize = 14.sp
                )
            }
            BasicTextField(
                value = query,
                onValueChange = onQueryChange,
                singleLine = true,
                textStyle = TextStyle(
                    color = Color(0xFFF8FAFC),
                    fontSize = 14.sp
                ),
                cursorBrush = SolidColor(Color(0xFF8B5CF6)),
                modifier = Modifier.fillMaxWidth()
            )
        }

        if (query.isNotEmpty()) {
            Text(
                text = "✕",
                color = Color(0xFF94A3B8),
                fontSize = 14.sp,
                modifier = Modifier
                    .clickable { onQueryChange("") }
                    .padding(4.dp)
            )
        }
    }
}

/**
 * 单张博客卡片
 */
@Composable
private fun BlogCard(
    blog: BlogMessage,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(BlogCardShape)
            .background(Color(0xFF131722).copy(alpha = 0.4f))
            .border(
                width = 1.dp,
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.White.copy(alpha = 0.15f),
                        Color.White.copy(alpha = 0.04f)
                    )
                ),
                shape = BlogCardShape
            )
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 左侧日期徽章
        Column(
            modifier = Modifier
                .width(72.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val parts = blog.time.split("-")
            Text(
                text = parts.getOrElse(2) { "" },
                color = Color(0xFFC4B5FD),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = parts.getOrElse(0) { blog.time } + "-" + parts.getOrElse(1) { "" },
                color = Color(0xFF64748B),
                fontSize = 10.sp
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = blog.title,
                color = Color(0xFFF8FAFC),
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = blog.briefContent,
                color = Color(0xFF94A3B8),
                fontSize = 13.sp,
                lineHeight = 19.sp,
                maxLines = 2
            )
        }
    }
}

/**
 * 全屏文章详情浮层：调用已完成的 MarkdownArticle 渲染正文
 */
@Composable
private fun BlogDetailOverlay(
    blog: BlogMessage,
    onClose: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0B0D13).copy(alpha = 0.92f))
            .clickable(onClick = onClose)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(Color(0xFF131722).copy(alpha = 0.95f))
                .border(
                    width = 1.dp,
                    color = Color.White.copy(alpha = 0.12f),
                    shape = RoundedCornerShape(24.dp)
                )
                .clickable { } // 消费点击事件，避免穿透到外层关闭浮层
                .padding(28.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = blog.title,
                        color = Color(0xFFF8FAFC),
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = blog.time,
                        color = Color(0xFF64748B),
                        fontSize = 12.sp
                    )
                }
                Text(
                    text = "✕ 关闭",
                    color = Color(0xFF94A3B8),
                    fontSize = 14.sp,
                    modifier = Modifier
                        .clickable(onClick = onClose)
                        .padding(6.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // TODO: 后端接口完成后替换为文章完整 markdown 正文
            MarkdownArticle(
                content = buildString {
                    appendLine("# ${blog.title}")
                    appendLine()
                    appendLine("> ${blog.time}")
                    appendLine()
                    append(blog.briefContent)
                },
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
