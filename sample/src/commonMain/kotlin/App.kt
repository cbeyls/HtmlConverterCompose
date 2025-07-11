import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.unit.dp
import be.digitalia.compose.htmlconverter.HtmlStyle
import be.digitalia.compose.htmlconverter.htmlToAnnotatedString

@Composable
fun App() {
    MaterialTheme {
        val scrollState = rememberScrollState()
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
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .padding(16.dp)
        ) {
            Text(
                text = convertedText,
                modifier = Modifier
                    .fillMaxSize()
                    .safeDrawingPadding(),
                style = MaterialTheme.typography.body1
            )
        }
    }
}