package main.unified

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import main.unified.components.AppScaffold
import main.unified.theme.UnifiedTheme

@Composable
@Preview
fun App() {
    UnifiedTheme {
        AppScaffold()
    }
}