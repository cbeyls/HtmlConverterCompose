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

import be.digitalia.compose.htmlconverter.HtmlHandler

/**
 * Simplified version of AnnotatedStringHtmlHandler without styling.
 */
internal class StringHtmlHandler(
    builder: StringBuilder,
    private val compactMode: Boolean
) : HtmlHandler {
    private val textWriter = HtmlTextWriter(builder)
    private var listLevel = 0
    private var preformattedLevel = 0
    private var skippedTagsLevel = 0

    override fun onOpenTag(name: String, attributes: (String) -> String?) {
        when (name) {
            "br" -> {}
            "p", "blockquote" -> handleBlockStart(2, 0)
            "div", "header", "footer", "main", "nav", "aside", "section", "article",
            "address", "figure", "figcaption",
            "video", "audio" -> handleBlockStart(1, 0)
            "ul", "ol", "dl" -> handleListStart()
            "li" -> handleListItemStart()
            "dt" -> handleDefinitionTermStart()
            "dd" -> handleDefinitionDetailStart()
            "pre" -> handlePreStart()
            "h1", "h2", "h3", "h4", "h5", "h6" -> handleHeadingStart()
            "script", "head", "table", "form", "fieldset" -> handleSkippedTagStart()
        }
    }

    private fun handleBlockStart(prefixNewLineCount: Int, indentCount: Int) {
        textWriter.startBlock(if (compactMode) 1 else prefixNewLineCount, indentCount)
    }

    private fun handleListStart() {
        handleBlockStart(if (listLevel == 0) 2 else 1, 0)
        listLevel++
    }

    private fun handleListItemStart() {
        handleBlockStart(1, listLevel - 1)
        textWriter.write("• ")
    }

    private fun handleDefinitionTermStart() {
        handleBlockStart(1, listLevel - 1)
    }

    private fun handleDefinitionDetailStart() {
        handleBlockStart(1, listLevel)
    }

    private fun handlePreStart() {
        handleBlockStart(2, 0)
        preformattedLevel++
    }

    private fun handleHeadingStart() {
        handleBlockStart(2, 0)
    }

    private fun handleSkippedTagStart() {
        skippedTagsLevel++
    }

    override fun onCloseTag(name: String) {
        when (name) {
            "br" -> handleLineBreakEnd()
            "p", "blockquote" -> handleBlockEnd(2)
            "div", "header", "footer", "main", "nav", "aside", "section", "article",
            "address", "figure", "figcaption",
            "video", "audio" -> handleBlockEnd(1)
            "ul", "ol", "dl" -> handleListEnd()
            "li" -> handleListItemEnd()
            "dt" -> handleDefinitionTermEnd()
            "dd" -> handleDefinitionDetailEnd()
            "pre" -> handlePreEnd()
            "h1", "h2", "h3", "h4", "h5", "h6" -> handleHeadingEnd()
            "script", "head", "table", "form", "fieldset" -> handleSkippedTagEnd()
        }
    }

    private fun handleLineBreakEnd() {
        textWriter.writeLineBreak()
    }

    private fun handleBlockEnd(suffixNewLineCount: Int) {
        textWriter.endBlock(if (compactMode) 1 else suffixNewLineCount)
    }

    private fun handleListEnd() {
        listLevel--
        handleBlockEnd(if (listLevel == 0) 2 else 1)
    }

    private fun handleListItemEnd() {
        handleBlockEnd(1)
    }

    private fun handleDefinitionTermEnd() {
        handleBlockEnd(1)
    }

    private fun handleDefinitionDetailEnd() {
        handleBlockEnd(1)
    }

    private fun handlePreEnd() {
        preformattedLevel--
        handleBlockEnd(2)
    }

    private fun handleHeadingEnd() {
        handleBlockEnd(1)
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
}