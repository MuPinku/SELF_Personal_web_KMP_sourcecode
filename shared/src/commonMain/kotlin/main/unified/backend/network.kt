package main.unified.backend



class Network {

}


data class BlogMessage(
    val title: String,
    val briefContent: String,
    val time: String,
)


fun blog_search_test(): List<BlogMessage> {
    return listOf(
        BlogMessage("KMP 跨平台实战指南", "从零搭建 KMP 项目，覆盖 Android、iOS 与 Web 三端统一架构设计", "2026-09-12"),
        BlogMessage("Compose for Web 性能优化", "深入剖析 Wasm 渲染管线瓶颈，提供列表虚拟滚动与懒加载最佳实践", "2026-09-10"),
        BlogMessage("Markdown 渲染器选型对比", "实测 multiplatform-markdown-renderer 0.43 在四端的兼容性与解析性能", "2026-09-08"),
        BlogMessage("Kotlin 协程异常处理进阶", "SupervisorJob 与 CoroutineExceptionHandler 的正确使用姿势与常见陷阱", "2026-09-05"),
        BlogMessage("Gradle 版本目录迁移手册", "将传统 buildSrc 平滑迁移至 Version Catalogs，提升多模块构建效率", "2026-09-03"),
        BlogMessage("KMP 网络层封装实践", "基于 Ktor 3.x 实现跨平台 HTTP 客户端，统一拦截器与错误重试策略", "2026-08-30"),
        BlogMessage("Compose 动画性能调优", "避免重组风暴：animateContentSize 与 snapshotFlow 的高效联动方案", "2026-08-27"),
        BlogMessage("KMP 本地数据存储方案", "SQLDelight vs Multiplatform Settings 适用场景分析与混合使用策略", "2026-08-24"),
        BlogMessage("WebAssembly 调试技巧大全", "Chrome DevTools 源码映射配置与 KMP Wasm 断点调试完整流程", "2026-08-20"),
        BlogMessage("KMP 单元测试最佳实践", "commonTest 中编写跨平台测试用例，Mock 依赖与异步验证标准化模板", "2026-08-15")
    )
}