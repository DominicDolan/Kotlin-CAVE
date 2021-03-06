package com.cave.library.matrix

import com.cave.library.angle.Degree
import com.cave.library.angle.Radian
import com.cave.library.angle.Rotation
import com.cave.library.matrix.mat3.StaticMatrix3
import com.cave.library.vector.vec3.Vector3
import com.cave.library.vector.vec3.dot
import kotlin.math.cos
import kotlin.math.sin


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
