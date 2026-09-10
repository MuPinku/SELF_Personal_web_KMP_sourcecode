### 配置图标

Compose Multiplatform 的资源插件（compose.components.resources）只识别 drawable/、values/、font/、files/ 
这四个预设目录。

在 shared/build.gradle.kts 的末尾添加了官方 Compose Multiplatform 提供的 compose.resources DSL 配置项：
```kotlin
compose.resources {
    publicResClass = true
    packageOfResClass = "main.unified.resources" // 关键：这一行强行改写了生成类的包名
    generateResClass = always
}
```

运行资源访问器生成任务
```bash
./gradlew :shared:generateResourceAccessorsForCommonMain
```


以上是一些常见的导入问题：


现在看导入后：

```kotlin
@Immutable
data class AppFonts(
    val regularFont: FontResource = Res.font.NotoSansSC_Regular,
)

val LocalAppFonts = staticCompositionLocalOf { AppFonts() }


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
```