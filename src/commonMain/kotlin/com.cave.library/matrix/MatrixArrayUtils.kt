package com.cave.library.matrix


fun Double.formatted(characterLength: Int = 6): String {
    val str = this.toString()
    return when {
        str.length < characterLength -> {
            getChars(' ', characterLength - str.length) + str
        }
        str.indexOf('.') < characterLength -> {
            str.substring(0, characterLength)
        }
        else -> str.substring(0, str.indexOf('.'))
    }
}
private const val emptyString = ""
private fun getChars(c: Char, count: Int): String {
    return (CharArray(count) { c }).joinToString(emptyString)
}
