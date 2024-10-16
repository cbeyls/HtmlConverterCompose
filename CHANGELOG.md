Change Log
==========

## Version 1.0.0

_2024-10-17_

New features:
- Clickable links are now handled automatically and will be opened using the platform's default browser
- Replace `linkSpanStyle` with `textLinkStyles` to allow custom link styles for focused, hovered and pressed states
- Add `linkInteractionListener` argument to allow overriding the default link click action.

Enhancements:
- Use the new `LinkAnnotation.Url` from Compose 1.7 in place of the deprecated `UrlAnnotation` to handle clickable anchor links.

This release depends on:
- Kotlin **2.0.21**
- Compose Multiplatform **1.7.0** (Jetpack Compose UI **1.7.1**)

## Version 0.9.5

_2024-03-29_

Updated dependencies.

This release depends on:
- Kotlin **1.9.23**
- Compose Multiplatform **1.6.1** (Jetpack Compose UI **1.6.3**)

## Version 0.9.4

_2024-01-24_

Bug fixes:
- Fix incorrect number of line breaks around empty blocks and paragraphs
- Fix incorrect block indentation after nested block.

This release depends on:
- Kotlin **1.9.21**
- Compose Multiplatform **1.5.11** (Jetpack Compose UI **1.5.4**)

## Version 0.9.3

_2024-01-22_

Bug fixes:
- Don't apply span styles on line breaks immediately preceding the opening tag
- Fix too many line breaks added when a `<br>` tag is followed by a simple block.

New features:
- Add basic support for `<hr>` (and `<hr/>`) tag for paragraph separation.

Enhancements:
- Only add span styles on non-empty portions of text 
- Improve memory usage when parsing unpaired tags.

This release depends on:
- Kotlin **1.9.21**
- Compose Multiplatform **1.5.11** (Jetpack Compose UI **1.5.4**)

## Version 0.9.1

_2024-01-03_

Bug fix:
- Properly support `<br>` and `</br>` tags in addition to `<br/>` tags. (Thanks to @tfcporciuncula for the report)

This release depends on:
- Kotlin **1.9.10**
- Compose Multiplatform **1.5.11** (Jetpack Compose UI **1.5.4**)

## Version 0.9.0

_2023-12-22_

New features:
- Support custom styling trough a new `HtmlStyle` argument (currently supports `linkSpanStyle` and `indentUnit`)
- Proper support for ordered lists, including nested ones
- Support for 2 levels of bold (bold and black)
- Headings are now shown in bold.

This release depends on:
- Kotlin **1.9.10**
- Compose Multiplatform **1.5.11** (Jetpack Compose UI **1.5.4**)

## Version 0.8.1

_2023-12-21_

Bug fix:
- Fix paragraph indent size for Compose Desktop (doesn't support em units yet, only sp).

New feature:
- Add a multiplatform sample app.

This release depends on:
- Kotlin **1.9.10**
- Compose Multiplatform **1.5.11** (Jetpack Compose UI **1.5.4**)

## Version 0.8.0

_2023-12-19_

Initial public release.
See README.md for details.

This release depends on:
- Kotlin **1.9.10**
- Compose Multiplatform **1.5.11** (Jetpack Compose UI **1.5.4**)