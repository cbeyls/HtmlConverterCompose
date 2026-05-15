import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.singleWindowApplication

fun main() = singleWindowApplication(title = "HTML Converter for Compose Sample") {
    App()
}

@Preview
@Composable
private fun AppDesktopPreview() {
    App()
}