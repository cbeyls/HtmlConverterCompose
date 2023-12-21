# HTML Converter for Compose Multiplatform

This library provides a simple API to convert HTML to Compose's [AnnotatedString](https://developer.android.com/reference/kotlin/androidx/compose/ui/text/AnnotatedString), including styling and paragraphs.
It can also be used to convert HTML to unstyled text.

It can be considered as a multiplatform replacement for Android's [`Html.fromHtml()`](https://developer.android.com/reference/android/text/Html#fromHtml(java.lang.String,%20int)) API with support for more tags and better performance.

| Platform      | Supported |
|---------------|----------|
| Android       | ✅        |
| Desktop (JVM) | ✅        |
| iOS           | ❌         |
| Web           | ❌         |

The iOS platform is not yet supported: testers and contributors are welcome.

The Web platform should use the [DOM wrapper API](https://kotlinlang.org/api/latest/jvm/stdlib/org.w3c.dom/) to insert HTML directly in the web page.

## Download

[![Maven Central](https://img.shields.io/maven-central/v/be.digitalia.compose.htmlconverter/htmlconverter)](https://central.sonatype.com/search?q=g:be.digitalia.compose.htmlconverter)

Add the dependency to your **module**'s `build.gradle` or `build.gradle.kts` file:

```kotlin
dependencies {
    implementation("be.digitalia.compose.htmlconverter:htmlconverter:0.8.1")
}
```

For Kotlin Multiplatform projects:

```kotlin
sourceSets {
    val commonMain by getting {
        dependencies {
            implementation("be.digitalia.compose.htmlconverter:htmlconverter:0.8.1")
        }
    }
}
```

## Usage

To display styled HTML in a Text composable:

```kotlin
val annotatedString = remember { htmlToAnnotatedString(html) }
Text(
    text = annotatedString,
    modifier = Modifier.fillMaxWidth()
)
```

To convert HTML to unstyled text:

```kotlin
val rawText = htmlToString(html)
```

Both functions take an optional `compactMode` boolean argument. When set to `true`, all paragraphs will be separated by a single line break instead of two, as it is normally the case for the tags: `p`, `blockquote`, `pre`, `ul`, `ol`, `dl`, `h1`, `h2`, `h3`, `h4`, `h5`, `h6`. The default value is `false`.

### Handling hyperlink clicks

Hyperlinks (inside the `a` tags) will be underlined and annotated with the experimental [`UrlAnnotation`](https://developer.android.com/reference/kotlin/androidx/compose/ui/text/UrlAnnotation). It is required to add custom code to detect clicks on these annotations and handle the navigation action accordingly.

For example, the `ClickableText` composable can be used, even if that solution is not perfect because it captures all touch events:

```kotlin
val annotatedString = remember { htmlToAnnotatedString(html) }
ClickableText(
    text = annotatedString,
    modifier = Modifier.fillMaxWidth(),
    onClick = { position ->
        annotatedString
            .getUrlAnnotations(position, position)
            .firstOrNull()?.let { range -> onLinkClick(range.item.url) }
    }
)
```

### Custom parsing

The `htmlToAnnotatedString()` and `htmlToString()` functions provide an overload that accepts an `HTMLParser` first argument in place of a `String`.

`HTMLParser` is an interface that you may implement to provide your own parser, in case the HTML is not directly available as a `String` (for example as a character stream or encoded using a binary format).

The default implementation uses the [KtXml](https://github.com/kobjects/ktxml) multiplatform XML parser library combined with extra code to handle HTML entities and invalid HTML.

## Supported HTML tags

- Inline tags with styling: `strong`, `b` (**bold**), `em`, `cite`, `dfn`, `i` (*italic*), `big` (bigger text), `small` (smaller text), `tt`, `code` (`monospace font`), `a` ([hyperlink](#supported-html-tags)), `u` (underline), `del`, `s`, `strike` (~~strikethrough~~), `sup` (<sup>supertext</sup>), `sub` (<sub>subtext</sub>)
- Block tags (paragraphs): `p`, `blockquote`, `pre` (including `monospace font`), `div`, `header`, `footer`, `main`, `nav`, `aside`, `section`, `article`, `address`, `figure`, `figcaption`, `video`, `audio` (no player shown, only inline text)
- Lists: `ul`, `ol` (entries are currently unnumbered, similar to `ul`), `li`, `dl`, `dt`, `dd`
- Section headings: `h1`, `h2`, `h3`, `h4`, `h5`, `h6`
- Line break: `br`

The following tags are skipped, along with their content: `script`, `head`, `table`, `form`, `fieldset`.

Others tags are ignored and replaced by their content, if any. For example, an `img` tag with inline replacement text will show the replacement text.

All HTML entities appearing in the text will be properly decoded as well.

## Used libraries

* [Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/) by JetBrains s.r.o.
* [KtXml](https://github.com/kobjects/ktxml) by Stefan Haustein

## What to expect from future versions

- Unit tests
- Proper support for ordered lists
- Better clickable hyperlinks support
- Support for displaying images as inline content
- iOS support (with help from the community).

## License

```
Copyright (C) 2023 Christophe Beyls
 
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```