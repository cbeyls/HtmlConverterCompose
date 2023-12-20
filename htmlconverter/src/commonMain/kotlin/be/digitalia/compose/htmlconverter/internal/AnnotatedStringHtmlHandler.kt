/*
 * Copyright (C) 2023 Christophe Beyls
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package be.digitalia.compose.htmlconverter.internal

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.UrlAnnotation
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.em
import be.digitalia.compose.htmlconverter.HtmlHandler

internal class AnnotatedStringHtmlHandler(
    private val builder: AnnotatedString.Builder,
    private val compactMode: Boolean,
    private val urlSpanStyle: SpanStyle
) : HtmlHandler {
    private val textWriter = HtmlTextWriter(builder)
    private var listLevel = 0
    private var preformattedLevel = 0
    private var skippedTagsLevel = 0
    private var blockStartIndex = -1
    private var blockIndentLevel = 0

    override fun onOpenTag(name: String, attributes: (String) -> String?) {
        when (name) {
            "br" -> {}
            "p" -> handleBlockStart(2, false)
            "blockquote" -> handleBlockStart(2, true)
            "div", "header", "footer", "main", "nav", "aside", "section", "article",
            "address", "figure", "figcaption",
            "video", "audio" -> handleBlockStart(1, false)
            "ul", "ol", "dl" -> handleListStart()
            "li" -> handleListItemStart()
            "dt" -> handleDefinitionTermStart()
            "dd" -> handleDefinitionDetailStart()
            "pre" -> handlePreStart()
            "strong", "b" -> handleSpanStyleStart(SpanStyle(fontWeight = FontWeight.Bold))
            "em", "cite", "dfn", "i" -> handleSpanStyleStart(SpanStyle(fontStyle = FontStyle.Italic))
            "big" -> handleSpanStyleStart(SpanStyle(fontSize = 1.25.em))
            "small" -> handleSpanStyleStart(SpanStyle(fontSize = 0.8.em))
            "tt", "code" -> handleSpanStyleStart(SpanStyle(fontFamily = FontFamily.Monospace))
            "a" -> handleAnchorStart(attributes("href").orEmpty())
            "u" -> handleSpanStyleStart(SpanStyle(textDecoration = TextDecoration.Underline))
            "del", "s", "strike" -> handleSpanStyleStart(SpanStyle(textDecoration = TextDecoration.LineThrough))
            "sup" -> handleSpanStyleStart(SpanStyle(baselineShift = BaselineShift.Superscript))
            "sub" -> handleSpanStyleStart(SpanStyle(baselineShift = BaselineShift.Subscript))
            "h1", "h2", "h3", "h4", "h5", "h6" -> handleHeadingStart(name)
            "script", "head", "table", "form", "fieldset" -> handleSkippedTagStart()
        }
    }

    /**
     * Add a pending paragraph, if any, and return the current index.
     */
    private fun addPendingParagraph(): Int {
        val currentIndex = builder.length
        // Close current paragraph, if any
        blockStartIndex.let { startIndex ->
            if (startIndex in 0..<currentIndex) {
                val indentSize = INDENT_UNIT * blockIndentLevel
                builder.addStyle(
                    style = ParagraphStyle(
                        textIndent = TextIndent(
                            firstLine = indentSize,
                            restLine = indentSize
                        )
                    ),
                    start = startIndex,
                    end = currentIndex
                )
            }
        }
        return currentIndex
    }

    private fun handleBlockStart(prefixNewLineCount: Int, indent: Boolean) {
        blockStartIndex = addPendingParagraph()
        if (indent) {
            blockIndentLevel++
        }
        // Paragraph style will automatically add one prefix new line
        textWriter.startBlock(if (compactMode) 0 else prefixNewLineCount - 1, 0)
    }

    private fun handleListStart() {
        handleBlockStart(if (listLevel == 0) 2 else 1, false)
        listLevel++
    }

    private fun handleListItemStart() {
        handleBlockStart(1, listLevel > 1)
        textWriter.write("• ")
    }

    private fun handleDefinitionTermStart() {
        handleBlockStart(1, false)
    }

    private fun handleDefinitionDetailStart() {
        handleBlockStart(1, true)
    }

    private fun handlePreStart() {
        handleBlockStart(2, false)
        handleSpanStyleStart(SpanStyle(fontFamily = FontFamily.Monospace))
        preformattedLevel++
    }

    private fun handleSpanStyleStart(style: SpanStyle) {
        builder.pushStyle(style)
    }

    @OptIn(ExperimentalTextApi::class)
    private fun handleAnchorStart(url: String) {
        builder.pushUrlAnnotation(UrlAnnotation(url))
        handleSpanStyleStart(urlSpanStyle)
    }

    private fun handleHeadingStart(name: String) {
        handleBlockStart(2, false)
        val level = name[1].digitToInt()
        handleSpanStyleStart(SpanStyle(fontSize = HEADING_SIZES[level - 1].em))
    }

    private fun handleSkippedTagStart() {
        skippedTagsLevel++
    }

    override fun onCloseTag(name: String) {
        when (name) {
            "br" -> handleLineBreakEnd()
            "p" -> handleBlockEnd(2, false)
            "blockquote" -> handleBlockEnd(2, true)
            "div", "header", "footer", "main", "nav", "aside", "section", "article",
            "address", "figure", "figcaption",
            "video", "audio" -> handleBlockEnd(1, false)
            "ul", "ol", "dl" -> handleListEnd()
            "li" -> handleListItemEnd()
            "dt" -> handleDefinitionTermEnd()
            "dd" -> handleDefinitionDetailEnd()
            "pre" -> handlePreEnd()
            "strong", "b",
            "em", "cite", "dfn", "i",
            "big",
            "small",
            "tt", "code",
            "u",
            "del", "s", "strike",
            "sup",
            "sub" -> handleSpanStyleEnd()
            "a" -> handleAnchorEnd()
            "h1", "h2", "h3", "h4", "h5", "h6" -> handleHeadingEnd()
            "script", "head", "table", "form", "fieldset" -> handleSkippedTagEnd()
        }
    }

    private fun handleLineBreakEnd() {
        textWriter.writeLineBreak()
    }

    private fun handleBlockEnd(suffixNewLineCount: Int, indent: Boolean) {
        // Paragraph style will automatically add one prefix new line
        textWriter.endBlock(if (compactMode) 0 else suffixNewLineCount - 1)
        addPendingParagraph()
        blockStartIndex = -1
        if (indent) {
            blockIndentLevel--
        }
    }

    private fun handleListEnd() {
        listLevel--
        handleBlockEnd(if (listLevel == 0) 2 else 1, false)
    }

    private fun handleListItemEnd() {
        handleBlockEnd(1, listLevel > 1)
    }

    private fun handleDefinitionTermEnd() {
        handleBlockEnd(1, false)
    }

    private fun handleDefinitionDetailEnd() {
        handleBlockEnd(1, true)
    }

    private fun handlePreEnd() {
        preformattedLevel--
        handleSpanStyleEnd()
        handleBlockEnd(2, false)
    }

    private fun handleSpanStyleEnd() {
        builder.pop()
    }

    private fun handleAnchorEnd() {
        handleSpanStyleEnd()
        builder.pop()
    }

    private fun handleHeadingEnd() {
        handleSpanStyleEnd()
        handleBlockEnd(1, false)
    }

    private fun handleSkippedTagEnd() {
        skippedTagsLevel--
    }

    override fun onText(text: String) {
        // Skip text inside skipped tags
        if (skippedTagsLevel > 0) {
            return
        }

        if (preformattedLevel == 0) {
            textWriter.write(text)
        } else {
            textWriter.writePreformatted(text)
        }
    }

    companion object {
        private val HEADING_SIZES = floatArrayOf(1.5f, 1.4f, 1.3f, 1.2f, 1.1f, 1f)
        private val INDENT_UNIT: TextUnit = 2.em
    }
}