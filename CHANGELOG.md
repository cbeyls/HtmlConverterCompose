Change Log
==========

## Version 0.9.3

_2024-01-22_

Bug fixes:
- Don't apply span styles on line breaks immediately preceding the opening tag
- Fix too many line breaks added when a <br> tag is followed by a simple block.

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