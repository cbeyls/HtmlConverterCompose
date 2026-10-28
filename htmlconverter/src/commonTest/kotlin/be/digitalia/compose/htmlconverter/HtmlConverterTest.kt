package be.digitalia.compose.htmlconverter

import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import kotlin.test.Test
import kotlin.test.assertEquals

class HtmlConverterTest {
    @Test
    fun simpleTest() {
        // language=html
        val html = """
            <a href="https://openfreemap.org" target="_blank">OpenFreeMap</a>
            <a href="https://www.openmaptiles.org/" target="_blank">&copy; OpenMapTiles</a>
            Data from <a href="https://www.openstreetmap.org/copyright" target="_blank">OpenStreetMap</a>
        """.trimIndent().trim()

        val expected = buildAnnotatedString {
            pushLink(
                LinkAnnotation.Url(
                    url = "https://openfreemap.org",
                    styles = TextLinkStyles(
                        style = SpanStyle(textDecoration = TextDecoration.Underline)
                    )
                )
            )
            append("OpenFreeMap")
            pop()

            append(" ")
            pushLink(
                LinkAnnotation.Url(
                    url = "https://www.openmaptiles.org/",
                    styles = TextLinkStyles(
                        style = SpanStyle(textDecoration = TextDecoration.Underline)
                    )
                )
            )
            append("© OpenMapTiles")
            pop()

            append(" Data from ")
            pushLink(
                LinkAnnotation.Url(
                    url = "https://www.openstreetmap.org/copyright",
                    styles = TextLinkStyles(
                        style = SpanStyle(textDecoration = TextDecoration.Underline)
                    )
                )
            )
            append("OpenStreetMap")
            pop()
        }

        assertEquals(expected, htmlToAnnotatedString(html))
    }

    @Test
    fun nonBreakingSpacesArePreserved() {
        assertEquals("© OpenStreetMap", htmlToString("©&nbsp;OpenStreetMap"))
        assertEquals("© OpenStreetMap", htmlToString("©&#160;OpenStreetMap"))
        assertEquals("© OpenStreetMap", htmlToString("© OpenStreetMap"))
        assertEquals("a   b", htmlToString("a &nbsp; b"))
    }

    @Test
    fun unicodeSpacesArePreserved() {
        assertEquals("1 000", htmlToString("1&thinsp;000"))
        assertEquals("東京　都", htmlToString("東京　都"))
    }

    @Test
    fun asciiWhitespaceIsCollapsed() {
        assertEquals("a b c d e", htmlToString("a \t b\n\nc\r\n d\u000C e"))
    }
}
