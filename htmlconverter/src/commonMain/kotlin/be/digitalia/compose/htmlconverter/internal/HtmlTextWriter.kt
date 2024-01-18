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

internal class HtmlTextWriter(private val output: Appendable) {
    private var currentState = STATE_BEGIN_TEXT
    // A negative value indicates new lines should be skipped for the next paragraph
    private var pendingNewLineCount = -1
    private var pendingIndentCount = 0
    private var hasTrailingNewLine = false

    fun markBlockTransition(newLineCount: Int, indentCount: Int) {
        pendingNewLineCount.let {
            if (it >= 0) {
                pendingNewLineCount = maxOf(it, newLineCount)
            }
        }
        pendingIndentCount = indentCount
        currentState = STATE_BEGIN_TEXT
    }

    /**
     * Skip leading whitespaces, and turn series of whitespaces into a single space.
     */
    fun write(text: String) {
        if (text.isEmpty()) {
            return
        }

        var state = currentState
        var index = 0
        while (true) {
            val contentStartIndex = text.indexOfFirst(index) { !it.isWhitespace() }
            // Add a single space after content if at least one leading whitespace is detected
            if (state == STATE_CONTENT_IN_PROGRESS && contentStartIndex != index) {
                output.append(' ')
            }
            if (contentStartIndex == -1) {
                // No content left, only spaces
                currentState = STATE_SPACE_IN_PROGRESS
                break
            }
            writePendingNewLines(false)

            index = text.indexOfFirst(contentStartIndex + 1) { it.isWhitespace() }
            if (index == -1) {
                // No spaces left, write remaining content
                output.append(text, contentStartIndex, text.length)
                currentState = STATE_CONTENT_IN_PROGRESS
                break
            }
            output.append(text, contentStartIndex, index)
            output.append(' ')
            state = STATE_SPACE_IN_PROGRESS
            index++
        }
    }

    fun writePreformatted(text: String) {
        if (text.isNotEmpty()) {
            writePendingNewLines(false)
            output.append(text)
        }
    }

    fun writeLineBreak() {
        writePendingNewLines(true)
        output.append('\n')
        currentState = STATE_BEGIN_TEXT
        hasTrailingNewLine = true
    }

    private inline fun CharSequence.indexOfFirst(startIndex: Int, predicate: (Char) -> Boolean): Int {
        for (index in startIndex..<length) {
            if (predicate(this[index])) {
                return index
            }
        }
        return -1
    }

    private fun writePendingNewLines(isLineBreak: Boolean) {
        var newLineCount = pendingNewLineCount
        if (newLineCount != 0) {
            if (newLineCount > 0) {
                if (hasTrailingNewLine) {
                    newLineCount--
                }
                repeat(newLineCount) {
                    output.append('\n')
                }
            }
            pendingNewLineCount = 0
        }
        pendingIndentCount.let { indentCount ->
            if (indentCount > 0) {
                for (i in 1..indentCount) {
                    output.append("    ")
                }
                pendingIndentCount = 0
            }
        }
        hasTrailingNewLine = isLineBreak
    }

    companion object {
        private const val STATE_BEGIN_TEXT = 0              // Don't write the first detected space
        private const val STATE_SPACE_IN_PROGRESS = 1       // Ignore new spaces
        private const val STATE_CONTENT_IN_PROGRESS = 2     // Write the first detected space
    }
}