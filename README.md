## [unified / Kotfolio]
基于 Kotlin Multiplatform (KMP) 构建的个人网站，一套代码，多端统一。
### 项目简介
这是一个使用 Kotlin Multiplatform 开发的个人网站项目。利用 KMP 的跨平台能力，核心逻辑与 UI 组件在 commonMain 中共享，
Web 端通过 jsMain 编译为 JavaScript 运行于浏览器。

技术栈: Kotlin Multiplatform, Compose Multiplatform (Web/Wasm), Gradle
目标平台: Web (JS/Wasm)

### 从源码到 jsMain 构建


在项目根目录执行以下命令，将 jsMain 编译为生产环境可用的静态资源：

```bash


# 生产构建（输出优化后的 JS 产物）
./gradlew jsBrowserProductionWebpack

# 开发构建（含 Source Map，便于调试）
./gradlew jsBrowserDevelopmentWebpack
```