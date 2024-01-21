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
    // A negative index means the list is unordered
    private var listIndexes: IntArray = EMPTY_LIST_INDEXES
    private var preformattedLevel = 0
    private var skippedTagsLevel = 0

    override fun onOpenTag(name: String, attributes: (String) -> String?) {
        when (name) {
            "br" -> handleLineBreakStart()
            "hr" -> handleHorizontalRuleStart()
            "p", "blockquote" -> handleBlockStart(2, 0)
            "div", "header", "footer", "main", "nav", "aside", "section", "article",
            "address", "figure", "figcaption",
            "video", "audio" -> handleBlockStart(1, 0)
            "ul", "dl" -> handleListStart(-1)
            "ol" -> handleListStart(1)
            "li" -> handleListItemStart()
            "dt" -> handleDefinitionTermStart()
            "dd" -> handleDefinitionDetailStart()
            "pre" -> handlePreStart()
            "h1", "h2", "h3", "h4", "h5", "h6" -> handleHeadingStart()
            "script", "head", "table", "form", "fieldset" -> handleSkippedTagStart()
        }
    }

    private fun handleLineBreakStart() {
        textWriter.writeLineBreak()
    }

    private fun handleHorizontalRuleStart() {
        textWriter.markBlockBoundary(if (compactMode) 1 else 2, 0)
    }

    private fun handleBlockStart(prefixNewLineCount: Int, indentCount: Int) {
        textWriter.markBlockBoundary(if (compactMode) 1 else prefixNewLineCount, indentCount)
    }

    private fun handleListStart(initialIndex: Int) {
        val currentListLevel = listLevel
        handleBlockStart(if (listLevel == 0) 2 else 1, 0)
        val listIndexesSize = listIndexes.size
        // Ensure listIndexes capacity
        if (currentListLevel == listIndexesSize) {
            listIndexes = if (listIndexesSize == 0) {
                IntArray(INITIAL_LIST_INDEXES_SIZE)
            } else {
                listIndexes.copyOf(listIndexesSize * 2)
            }
        }
        listIndexes[currentListLevel] = initialIndex
        listLevel = currentListLevel + 1
    }

    private fun handleListItemStart() {
        val currentListLevel = listLevel
        handleBlockStart(1, currentListLevel - 1)
        val itemIndex = if (currentListLevel == 0) {
            -1
        } else {
            listIndexes[currentListLevel - 1]
        }
        if (itemIndex < 0) {
            textWriter.write("• ")
        } else {
            textWriter.write(itemIndex.toString())
            textWriter.write(". ")
            listIndexes[currentListLevel - 1] = itemIndex + 1
        }
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
            "br",
            "hr" -> {}
            "p", "blockquote" -> handleBlockEnd(2)
            "div", "header", "footer", "main", "nav", "aside", "section", "article",
            "address", "figure", "figcaption",
            "video", "audio" -> handleBlockEnd(1)
            "ul", "dl",
            "ol" -> handleListEnd()
            "li" -> handleListItemEnd()
            "dt" -> handleDefinitionTermEnd()
            "dd" -> handleDefinitionDetailEnd()
            "pre" -> handlePreEnd()
            "h1", "h2", "h3", "h4", "h5", "h6" -> handleHeadingEnd()
            "script", "head", "table", "form", "fieldset" -> handleSkippedTagEnd()
        }
    }

    private fun handleBlockEnd(suffixNewLineCount: Int) {
        textWriter.markBlockBoundary(if (compactMode) 1 else suffixNewLineCount, 0)
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

    companion object {
        private val EMPTY_LIST_INDEXES = intArrayOf()
        private const val INITIAL_LIST_INDEXES_SIZE = 8
    }
}