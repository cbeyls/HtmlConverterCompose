import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "HTML Converter for Compose Sample") {
        App()
    }
}

@Preview
@Composable
private fun AppDesktopPreview() {
    App()
}