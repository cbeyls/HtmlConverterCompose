[![Continuous Integration](https://github.com/cbeyls/HtmlConverterCompose/actions/workflows/ci.yml/badge.svg)](https://github.com/cbeyls/HtmlConverterCompose/actions/workflows/ci.yml)

# HTML Converter for Compose Multiplatform

This library provides a simple API to convert HTML to Compose's [AnnotatedString](https://developer.android.com/reference/kotlin/androidx/compose/ui/text/AnnotatedString), including styling and paragraphs.
It can also be used to convert HTML to unstyled text.

It can be considered as a multiplatform replacement for Android's [`Html.fromHtml()`](https://developer.android.com/reference/android/text/Html#fromHtml(java.lang.String,%20int)) API with support for more tags and better performance.

| Platform                | Supported |
|-------------------------|-----------|
| JVM (Android & Desktop) | ✅         |
| Native (iOS & macOS)    | ✅         |
| Web (JS & WASM)         | ✅         |

## Running the sample app

#### Android

* Open the project in Android Studio and click ▶ **sample** (run configuration) to build and run.
* _OR_ from the command line: `./gradlew sample:installDebug` to install to a connected device.

#### Desktop (JVM)

* From the command line: `./gradlew sample:run`

#### iOS

* Open `iosApp/Sample.xcodeproj` in XCode.
* Click ▶ **Sample** to build and run.

#### Desktop (JVM) and iOS with Fleet or Android Studio

If you have a recent version of Fleet installed, the platform targets should automatically be
detected opening the project. Click ▶ to select a target device, build and run.

Alternatively, you can use the [Kotlin Multiplatform Plugin](https://plugins.jetbrains.com/plugin/14936-kotlin-multiplatform)
with Android Studio. Follow the [Run your application](https://www.jetbrains.com/help/kotlin-multiplatform-dev/compose-multiplatform-create-first-app.html#run-your-application)
instructions to select or set up a run configuration for each target.

## Download

[![Maven Central](https://img.shields.io/maven-central/v/be.digitalia.compose.htmlconverter/htmlconverter)](https://central.sonatype.com/search?q=g:be.digitalia.compose.htmlconverter)

Add the dependency to your **module**'s `build.gradle` or `build.gradle.kts` file:

```kotlin
dependencies {
    implementation("be.digitalia.compose.htmlconverter:htmlconverter:1.0.4")
}
```

For Kotlin Multiplatform projects:

```kotlin
sourceSets {
    val commonMain by getting {
        dependencies {
            implementation("be.digitalia.compose.htmlconverter:htmlconverter:1.0.4")
        }
    }
}
```

## Usage

To display styled HTML in a Text composable:

```kotlin
Text(text = remember(html) { htmlToAnnotatedString(html) })
```

> If called from inside a `@Composable` function, in most cases it is recommended to use `remember()` to cache the result of the conversion, to avoid recomputation on each recomposition.

To convert HTML to unstyled text:

```kotlin
val rawText = htmlToString(html)
```

Both functions take an optional `compactMode` boolean argument. When set to `true`, all paragraphs will be separated by a single line break instead of two, as it is normally the case for the tags: `p`, `blockquote`, `pre`, `ul`, `ol`, `dl`, `h1`, `h2`, `h3`, `h4`, `h5`, `h6`. The default value is `false`.

### Custom styling

The `htmlToAnnotatedString()` function takes an optional `style` argument of type `HtmlStyle` which allows to customize styling. The currently provided options are:

- `textLinkStyles`: Optional collection of styles for hyperlinks (content of `a` tags). Default is a simple underline. When set to `null`, hyperlinks will not be styled, which can be useful when they are not clickable (see following section).
- `indentUnit`: Unit of indentation for block quotations and nested lists. Default is **24 sp**. Note that `em` units are not yet supported for indentation in Compose Desktop. Set to `0.sp` or `TextUnit.Unspecified` to disable indentation support.
- `isTextColorEnabled`: Enable CSS colored text support (see next section).

For example, here is how to style hyperlinks to use the theme's primary color with no underline:

```kotlin
val linkColor = MaterialTheme.colors.primary
val convertedText = remember(html, linkColor) {
    htmlToAnnotatedString(
        html,
        style = HtmlStyle(
            textLinkStyles = TextLinkStyles(
                style = SpanStyle(color = linkColor)
            )
        )
    )
}
Text(text = convertedText)
```

### Colored text

Support for CSS colored text is disabled by default in order to guarantee good contrast and respect of the Compose theme colors for unsanitized HTML input. To enable CSS colored text, set the `isTextColorEnabled` option to `true` in the `HtmlStyle` argument:

```kotlin
val convertedText = remember(html) {
    htmlToAnnotatedString(
        html,
        style = HtmlStyle(isTextColorEnabled = true)
    )
}
Text(text = convertedText)
```

- Text coloring is available for supported HTML **inline** tags only, through the CSS `"style"` attribute: `span`, `strong`, `b`, `em`, `cite`, `dfn`, `i`, `big`, `small`, `tt`, `code`, `a`, `u`, `del`, `s`, `strike`, `sup`, `sub`.
- Both foreground (`color`) and background (`background`, `background-color`) CSS properties are supported.
- All [CSS level 4 named colors](https://www.w3.org/TR/css-color-4/#named-colors) (case insensitive) and all hexadecimal RGB color definitions (`#RRGGBB`, `#RRGGBBAA`, `#RGB`, `#RGBA`) are supported.
- Other color formats like `rgb(...)`, `rgba(...)`, `hsl(...)` and `hsla(...)` are **not** supported.
- If a CSS color property is defined on a hyperlink or on a tag inside a hyperlink, it will override any color defined in `textLinkStyles`.  

Example of supported CSS color styling:

```html
<span style="color: darkred; background-color: #FFFF0099">Red over translucent yellow</span>
```

### Handling hyperlink clicks

Hyperlinks (content of `a` tags) will be annotated with [`LinkAnnotation.Url`](https://developer.android.com/reference/kotlin/androidx/compose/ui/text/LinkAnnotation.Url). When clicked, they will automatically be handled by the default [`UriHandler`](https://developer.android.com/reference/kotlin/androidx/compose/ui/platform/UriHandler) which will open them using the platform's default browser.

To override that behavior or disable click handling completely, specify a custom `linkInteractionListener` argument:

```kotlin
val convertedText = remember(html) {
    htmlToAnnotatedString(
        html,
        linkInteractionListener = { link ->
            if (link is LinkAnnotation.Url) {
                navigateTo(link.url)
            }
        }
    )
}
Text(text = convertedText)
```

### Bug when showing hyperlinks in combination with maxLines in Compose 1.7

Compose UI versions 1.7.0 to 1.7.6 have a bug which triggers a crash when a `Text` composable using `maxLines` is displaying an `AnnotatedString` containing a `LinkAnnotation` inside a paragraph.

This library is vulnerable to that bug because it uses both `LinkAnnotation` to display hyperlinks and paragraphs to handle text indentation.

It is recommended to upgrade to Compose 1.8.0 or more recent to avoid the issue.

If you are still using Compose 1.7.x along with an older version of this library, as a workaround, you can disable indentation support in `Text` composables which require the usage of `maxLines`:

```kotlin
val convertedText = remember(html) {
    htmlToAnnotatedString(
        html,
        style = HtmlStyle(indentUnit = TextUnit.Unspecified)
    )
}
Text(
    text = convertedText,
    maxLines = 3
)
```

See related bug reports [374115892](https://issuetracker.google.com/issues/374115892), [372390054](https://issuetracker.google.com/issues/372390054) on the Google issue tracker.

### Custom parsing

The `htmlToAnnotatedString()` and `htmlToString()` functions provide an overload that accepts a `HTMLParser` first argument in place of a `String`.

`HTMLParser` is an interface that you may implement to provide your own parser, in case the HTML is not directly available as a `String` (for example as a character stream or encoded using a binary format).

The default implementation uses the [KtXml](https://github.com/kobjects/ktxml) multiplatform XML parser library combined with extra code to handle HTML entities and invalid HTML.

## Supported HTML tags

- Inline tags with styling: `strong`, `b` (**bold**), `em`, `cite`, `dfn`, `i` (*italic*), `big` (bigger text), `small` (smaller text), `tt`, `code` (`monospace font`), `a` ([hyperlink](#supported-html-tags)), `u` (underline), `del`, `s`, `strike` (~~strikethrough~~), `sup` (<sup>supertext</sup>), `sub` (<sub>subtext</sub>), `span` (CSS colors only through the "style" attribute)
- Block tags (paragraphs): `p`, `blockquote`, `pre` (including `monospace font`), `div`, `header`, `footer`, `main`, `nav`, `aside`, `section`, `article`, `address`, `figure`, `figcaption`, `video`, `audio` (no player shown, only inline text)
- Horizontal rule: `hr` (no line drawn, but marks a new paragraph)
- Lists: `ul`, `ol`, `li`, `dl`, `dt`, `dd`
- Section headings: `h1`, `h2`, `h3`, `h4`, `h5`, `h6`
- Line break: `br`

The following tags are skipped, along with their content: `script`, `head`, `table`, `form`, `fieldset`.

Others tags are ignored and replaced by their content, if any.

All HTML entities appearing in the text will be properly decoded as well.

## Used libraries

* [Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/) by JetBrains s.r.o.
* [KtXml](https://github.com/kobjects/ktxml) by Stefan Haustein

## What to expect from future versions

- Unit tests
- Support for displaying images as inline content

## License

```
Copyright (C) 2023-2025 Christophe Beyls and contributors
 
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