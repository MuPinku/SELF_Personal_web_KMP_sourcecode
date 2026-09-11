禁止混用 Android XML：res/drawable/*.xml 无法跨平台，所有图标必须统一放入 commonMain/composeResources/drawable/。

禁止强求所有 SVG 转 ImageVector：含滤镜、复杂渐变、虚线的 SVG 强行转换会导致渲染异常或编译失

使用：Image + painterResource代替
95% 的图标场景，统一使用 Image 组件替代 Icon

不用试图导入 Material3 的内置 icons 库，web 不支持