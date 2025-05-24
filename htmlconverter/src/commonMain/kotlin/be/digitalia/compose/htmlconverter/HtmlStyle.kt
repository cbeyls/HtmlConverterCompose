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
package be.digitalia.compose.htmlconverter

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Immutable
public data class HtmlStyle(
    /**
     * Optional style for hyperlinks (<a> tags). Default is a simple underline.
     */
    val textLinkStyles: TextLinkStyles? = TextLinkStyles(
        style = SpanStyle(textDecoration = TextDecoration.Underline)
    ),
    /**
     * Unit of indentation for block quotations and nested lists. Default is 24 sp.
     */
    val indentUnit: TextUnit = 24.sp,
    /**
     * Enable support for text foreground and background color using CSS inline styles. Default is disabled.
     */
    val isTextColorEnabled: Boolean = false
) {
    public companion object {
        public val DEFAULT: HtmlStyle = HtmlStyle()
    }
}