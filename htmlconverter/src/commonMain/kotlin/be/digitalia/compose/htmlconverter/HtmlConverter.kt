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
@file:JvmName("HtmlConverter")

package be.digitalia.compose.htmlconverter

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.LinkInteractionListener
import be.digitalia.compose.htmlconverter.internal.AnnotatedStringHtmlHandler
import be.digitalia.compose.htmlconverter.internal.StringHtmlHandler
import be.digitalia.compose.htmlconverter.internal.parser.KtXmlParser
import kotlin.jvm.JvmName

/**
 * Convert HTML to AnnotatedString using the built-in parser.
 */
public fun htmlToAnnotatedString(
    html: String,
    compactMode: Boolean = false,
    style: HtmlStyle = HtmlStyle.DEFAULT,
    linkInteractionListener: LinkInteractionListener? = null
): AnnotatedString {
    return htmlToAnnotatedString(
        KtXmlParser(html.iterator()),
        compactMode,
        style,
        linkInteractionListener
    )
}

/**
 * Convert HTML to AnnotatedString using the provided parser.
 */
public fun htmlToAnnotatedString(
    parser: HtmlParser,
    compactMode: Boolean = false,
    style: HtmlStyle = HtmlStyle.DEFAULT,
    linkInteractionListener: LinkInteractionListener? = null
): AnnotatedString {
    val builder = AnnotatedString.Builder()
    parser.parse(AnnotatedStringHtmlHandler(builder, compactMode, style, linkInteractionListener))
    return builder.toAnnotatedString()
}

/**
 * Convert HTML to regular text using the built-in parser,
 * stripping tags and adding extra whitespaces and line breaks for paragraphs.
 */
public fun htmlToString(html: String, compactMode: Boolean = false): String {
    return htmlToString(KtXmlParser(html.iterator()), compactMode)
}

/**
 * Convert HTML to regular text using the provided parser,
 * stripping tags and adding extra whitespaces and line breaks for paragraphs.
 */
public fun htmlToString(parser: HtmlParser, compactMode: Boolean = false): String {
    val builder = StringBuilder()
    parser.parse(StringHtmlHandler(builder, compactMode))
    return builder.toString()
}