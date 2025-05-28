package be.digitalia.compose.htmlconverter.internal

import androidx.compose.ui.graphics.Color

private val COLOR_NAMES = listOf(
    "AliceBlue",
    "AntiqueWhite",
    "Aqua",
    "Aquamarine",
    "Azure",
    "Beige",
    "Bisque",
    "Black",
    "BlanchedAlmond",
    "Blue",
    "BlueViolet",
    "Brown",
    "BurlyWood",
    "CadetBlue",
    "Chartreuse",
    "Chocolate",
    "Coral",
    "CornflowerBlue",
    "Cornsilk",
    "Crimson",
    "Cyan",
    "DarkBlue",
    "DarkCyan",
    "DarkGoldenRod",
    "DarkGray",
    "DarkGrey",
    "DarkGreen",
    "DarkKhaki",
    "DarkMagenta",
    "DarkOliveGreen",
    "DarkOrange",
    "DarkOrchid",
    "DarkRed",
    "DarkSalmon",
    "DarkSeaGreen",
    "DarkSlateBlue",
    "DarkSlateGray",
    "DarkSlateGrey",
    "DarkTurquoise",
    "DarkViolet",
    "DeepPink",
    "DeepSkyBlue",
    "DimGray",
    "DimGrey",
    "DodgerBlue",
    "FireBrick",
    "FloralWhite",
    "ForestGreen",
    "Fuchsia",
    "Gainsboro",
    "GhostWhite",
    "Gold",
    "GoldenRod",
    "Gray",
    "Grey",
    "Green",
    "GreenYellow",
    "HoneyDew",
    "HotPink",
    "IndianRed",
    "Indigo",
    "Ivory",
    "Khaki",
    "Lavender",
    "LavenderBlush",
    "LawnGreen",
    "LemonChiffon",
    "LightBlue",
    "LightCoral",
    "LightCyan",
    "LightGoldenRodYellow",
    "LightGray",
    "LightGrey",
    "LightGreen",
    "LightPink",
    "LightSalmon",
    "LightSeaGreen",
    "LightSkyBlue",
    "LightSlateGray",
    "LightSlateGrey",
    "LightSteelBlue",
    "LightYellow",
    "Lime",
    "LimeGreen",
    "Linen",
    "Magenta",
    "Maroon",
    "MediumAquaMarine",
    "MediumBlue",
    "MediumOrchid",
    "MediumPurple",
    "MediumSeaGreen",
    "MediumSlateBlue",
    "MediumSpringGreen",
    "MediumTurquoise",
    "MediumVioletRed",
    "MidnightBlue",
    "MintCream",
    "MistyRose",
    "Moccasin",
    "NavajoWhite",
    "Navy",
    "OldLace",
    "Olive",
    "OliveDrab",
    "Orange",
    "OrangeRed",
    "Orchid",
    "PaleGoldenRod",
    "PaleGreen",
    "PaleTurquoise",
    "PaleVioletRed",
    "PapayaWhip",
    "PeachPuff",
    "Peru",
    "Pink",
    "Plum",
    "PowderBlue",
    "Purple",
    "RebeccaPurple",
    "Red",
    "RosyBrown",
    "RoyalBlue",
    "SaddleBrown",
    "Salmon",
    "SandyBrown",
    "SeaGreen",
    "SeaShell",
    "Sienna",
    "Silver",
    "SkyBlue",
    "SlateBlue",
    "SlateGray",
    "SlateGrey",
    "Snow",
    "SpringGreen",
    "SteelBlue",
    "Tan",
    "Teal",
    "Thistle",
    "Tomato",
    "Turquoise",
    "Violet",
    "Wheat",
    "White",
    "WhiteSmoke",
    "Yellow",
    "YellowGreen"
)

private val COLOR_VALUES = intArrayOf(
    0xF0F8FF,
    0xFAEBD7,
    0x00FFFF,
    0x7FFFD4,
    0xF0FFFF,
    0xF5F5DC,
    0xFFE4C4,
    0x000000,
    0xFFEBCD,
    0x0000FF,
    0x8A2BE2,
    0xA52A2A,
    0xDEB887,
    0x5F9EA0,
    0x7FFF00,
    0xD2691E,
    0xFF7F50,
    0x6495ED,
    0xFFF8DC,
    0xDC143C,
    0x00FFFF,
    0x00008B,
    0x008B8B,
    0xB8860B,
    0xA9A9A9,
    0xA9A9A9,
    0x006400,
    0xBDB76B,
    0x8B008B,
    0x556B2F,
    0xFF8C00,
    0x9932CC,
    0x8B0000,
    0xE9967A,
    0x8FBC8F,
    0x483D8B,
    0x2F4F4F,
    0x2F4F4F,
    0x00CED1,
    0x9400D3,
    0xFF1493,
    0x00BFFF,
    0x696969,
    0x696969,
    0x1E90FF,
    0xB22222,
    0xFFFAF0,
    0x228B22,
    0xFF00FF,
    0xDCDCDC,
    0xF8F8FF,
    0xFFD700,
    0xDAA520,
    0x808080,
    0x808080,
    0x008000,
    0xADFF2F,
    0xF0FFF0,
    0xFF69B4,
    0xCD5C5C,
    0x4B0082,
    0xFFFFF0,
    0xF0E68C,
    0xE6E6FA,
    0xFFF0F5,
    0x7CFC00,
    0xFFFACD,
    0xADD8E6,
    0xF08080,
    0xE0FFFF,
    0xFAFAD2,
    0xD3D3D3,
    0xD3D3D3,
    0x90EE90,
    0xFFB6C1,
    0xFFA07A,
    0x20B2AA,
    0x87CEFA,
    0x778899,
    0x778899,
    0xB0C4DE,
    0xFFFFE0,
    0x00FF00,
    0x32CD32,
    0xFAF0E6,
    0xFF00FF,
    0x800000,
    0x66CDAA,
    0x0000CD,
    0xBA55D3,
    0x9370DB,
    0x3CB371,
    0x7B68EE,
    0x00FA9A,
    0x48D1CC,
    0xC71585,
    0x191970,
    0xF5FFFA,
    0xFFE4E1,
    0xFFE4B5,
    0xFFDEAD,
    0x000080,
    0xFDF5E6,
    0x808000,
    0x6B8E23,
    0xFFA500,
    0xFF4500,
    0xDA70D6,
    0xEEE8AA,
    0x98FB98,
    0xAFEEEE,
    0xDB7093,
    0xFFEFD5,
    0xFFDAB9,
    0xCD853F,
    0xFFC0CB,
    0xDDA0DD,
    0xB0E0E6,
    0x800080,
    0x663399,
    0xFF0000,
    0xBC8F8F,
    0x4169E1,
    0x8B4513,
    0xFA8072,
    0xF4A460,
    0x2E8B57,
    0xFFF5EE,
    0xA0522D,
    0xC0C0C0,
    0x87CEEB,
    0x6A5ACD,
    0x708090,
    0x708090,
    0xFFFAFA,
    0x00FF7F,
    0x4682B4,
    0xD2B48C,
    0x008080,
    0xD8BFD8,
    0xFF6347,
    0x40E0D0,
    0xEE82EE,
    0xF5DEB3,
    0xFFFFFF,
    0xF5F5F5,
    0xFFFF00,
    0x9ACD32
)

/**
 * Return the matching color if present in the color names list or Color.Unspecified if absent.
 */
private fun getColorByName(colorName: String): Color {
    val index = COLOR_NAMES.binarySearch(colorName, String.CASE_INSENSITIVE_ORDER)
    return if (index < 0) Color.Unspecified else Color(COLOR_VALUES[index].toLong() or 0xFF000000L)
}

private val Char.hexDigitValue: Int
    get() = when (this) {
        in '0'..'9' -> this - '0'
        in 'a'..'f' -> this - 'a' + 10
        in 'A'..'F' -> this - 'A' + 10
        else -> throw IllegalArgumentException("Invalid hex digit")
    }

private fun String.parseHexToInt(): Int {
    val l = length
    require(l in 2..9) { "Invalid number of hex digits" }
    var result = 0
    // Start at position 1 (skip the '#')
    for (i in 1..<l) {
        result = (result shl 4) or this[i].hexDigitValue
    }
    return result
}

/**
 * Transform color from RRGGBBAA to AARRGGBB format
 */
private fun swapColorAlpha(colorValue: Int): Int {
    val alpha = colorValue and 0xFF
    return (colorValue ushr 8) or (alpha shl 24)
}

/**
 * Transform color from RGB to RRGGBB format or from RGBA to RRGGBBAA format
 */
private fun expandColor(colorValue: Int, length: Int): Int {
    var remainingValue = colorValue
    var maskPosition = 0
    var result = 0
    repeat(length) {
        val component = remainingValue and 0xF
        result = result or (component shl maskPosition)
        maskPosition += 4
        result = result or (component shl maskPosition)
        maskPosition += 4
        remainingValue = remainingValue ushr 4
    }
    return result
}

private fun String.hexToColor(): Color {
    val colorValue = try {
        parseHexToInt()
    } catch (_: IllegalArgumentException) {
        return Color.Unspecified
    }
    return when (length) {
        // #RRGGBB
        7 -> Color(colorValue or 0xFF000000.toInt())
        // #RRGGBBAA
        9 -> Color(swapColorAlpha(colorValue))
        // #RGB
        4 -> Color(expandColor(colorValue, 3) or 0xFF000000.toInt())
        // #RGBA
        5 -> Color(swapColorAlpha(expandColor(colorValue, 4)))
        else -> Color.Unspecified
    }
}

internal fun getHtmlColor(value: String): Color {
    if (value.isEmpty()) {
        return Color.Unspecified
    }
    val firstChar = value[0]
    return when {
        firstChar == '#' -> value.hexToColor()
        firstChar.isLetter() -> getColorByName(value)
        else -> Color.Unspecified
    }
}

private val COLOR_STYLE_REGEX = Regex("(?:^|;)\\s*color\\s*:\\s*(.*?)(?:$|\\s|;)", RegexOption.IGNORE_CASE)

internal fun getColorFromStyle(style: String): Color {
    val matchResult = COLOR_STYLE_REGEX.find(style)
    return if (matchResult != null) {
        getHtmlColor(matchResult.groupValues[1])
    } else {
        Color.Unspecified
    }
}

private val BACKGROUND_COLOR_STYLE_REGEX =
    Regex("(?:^|;)\\s*background(?:-color)?\\s*:\\s*(.*?)(?:$|\\s|;)", RegexOption.IGNORE_CASE)

internal fun getBackgroundColorFromStyle(style: String): Color {
    val matchResult = BACKGROUND_COLOR_STYLE_REGEX.find(style)
    return if (matchResult != null) {
        getHtmlColor(matchResult.groupValues[1])
    } else {
        Color.Unspecified
    }
}