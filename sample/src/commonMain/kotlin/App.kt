import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.unit.dp
import be.digitalia.compose.htmlconverter.HtmlStyle
import be.digitalia.compose.htmlconverter.htmlToAnnotatedString

private val LightColorPalette = lightColors()
private val DarkColorPalette = darkColors()

@Composable
fun App() {
    MaterialTheme(
        colors = if (isSystemInDarkTheme()) DarkColorPalette else LightColorPalette
    ) {
        val linkColor = MaterialTheme.colors.primary
        val convertedText = remember(linkColor) {
            htmlToAnnotatedString(
                SampleHtml,
                style = HtmlStyle(
                    textLinkStyles = TextLinkStyles(
                        style = SpanStyle(color = linkColor)
                    ),
                    isTextColorEnabled = true
                )
            )
        }

        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                Text(
                    text = convertedText,
                    modifier = Modifier
                        .fillMaxSize()
                        .safeDrawingPadding(),
                    style = MaterialTheme.typography.body1.copy(
                        lineBreak = LineBreak.Paragraph
                    )
                )
            }
        }
    }
}