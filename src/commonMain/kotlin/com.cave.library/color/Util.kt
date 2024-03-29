package com.cave.library.color

import com.cave.library.angle.Angle
import com.cave.library.angle.degrees
import com.cave.library.vector.vec4.Vector4
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min


fun hex(color: Long) = InlineColor(color)

fun rgba(r: Double, g: Double, b: Double, a: Double) = InlineColor(rgba2Hex(r, g, b, a))

fun Color.linearBlend(p: Double, other: Color): InlineColor {
    val (r0, g0, b0, a0) = this
    val (r1, g1, b1, a1) = other

    fun blend(v0: Double, v1: Double, p: Double) = v0*(1-p)+v1*p

    val alpha = blend(a0, a1, p)
    val red = blend(r0, r1, p)
    val green = blend(g0, g1, p)
    val blue = blend(b0, b1, p)

    return InlineColor(rgba2Hex(red, green, blue, alpha))
}

fun Color.logBlend(percent: Double, other: Color): Color {
    val p = percent%100.0
    val (r0, g0, b0, a0) = this
    val (r1, g1, b1, a1) = other

    fun Double.sqrd() = this*this
    fun Double.sqrt() = kotlin.math.sqrt(this)
    fun blend(v0: Double, v1: Double, p: Double) = (((1-p)*v0.sqrd()+p*v1.sqrd())).sqrt()

    val alpha= a0*(1-p)+a1*p
    val red = blend(r0, r1, p)
    val green = blend(g0, g1, p)
    val blue = blend(b0, b1, p)

    return InlineColor(rgba2Hex(red, green, blue, alpha))
}


fun Color.alphaBlend(src: Color): InlineColor {

    val alpha = src.a + a*(1 - src.a)
    val red = alphaBlendComponent(0, src, this)
    val green = alphaBlendComponent(1, src, this)
    val blue = alphaBlendComponent(2, src, this)
    return rgba(red, green, blue, alpha)
}

private fun alphaBlendComponent(index: Int, src: Color, dst: Color): Double {
    val srcComponent = src[index]
    val dstComponent = dst[index]
    val alpha = src.a
    return srcComponent*alpha + dstComponent*(1 - alpha)
}

fun InlineColor.alphaBlend(src: InlineColor): InlineColor {

    val alpha = src.a + a*(1 - src.a)
    val red = alphaBlendComponent(0, src, this)
    val green = alphaBlendComponent(1, src, this)
    val blue = alphaBlendComponent(2, src, this)
    return rgba(red, green, blue, alpha)
}

private fun alphaBlendComponent(index: Int, src: InlineColor, dst: InlineColor): Double {
    val srcComponent = src[index]
    val dstComponent = dst[index]
    val alpha = src.a
    return srcComponent*alpha + dstComponent*(1 - alpha)
}

fun Color.lighten(amount: Int): Color {
    return InlineColor(adjustShade(this.toLong(), amount))
}

fun Color.darken(amount: Int): Color {
    return InlineColor(adjustShade(this.toLong(), -amount))
}

private fun adjustShade(hex: Long, amount: Int): Long {
    fun adjust(shift: Int): Long {
        val shifted = (hex shr shift and 0xFF)
        val willOverflow = amount > 0 && shifted > 0xFF - amount
        val willUnderflow = amount < 0 && shifted < 0x00 - amount
        return if (willOverflow) 0xFF else if (willUnderflow) 0x00 else shifted + amount
    }

    val r = adjust(24)
    val g = adjust(16)
    val b = adjust(8)
    val a = (hex and 0xFF)

    return a or (b shl 8) or (g shl 16) or (r shl 24)
}


fun Color.lightness(level: Double): InlineColor {
    return hsl(hue, saturation, level)
}

fun Color.hue(level: Angle): InlineColor {
    return hsl(level, saturation, lightness)
}

fun Color.saturation(level: Double): InlineColor {
    return hsl(hue, level, lightness)
}

fun Color.alpha(value: Double): InlineColor {
    return rgba(r, g, b, value)
}

fun rgba2Hex(red: Double, green: Double, blue: Double, alpha: Double): Long {
    val a = (alpha*255).toLong()
    val b = (blue*255).toLong() shl 8
    val g = (green*255).toLong() shl 16
    val r = (red*255).toLong() shl 24
    return r + b + g + a
}

fun hex2Alpha(hex: Long) = (hex and 0xFF)/255.0
fun hex2Blue(hex: Long) = (hex shr 8 and 0xFF)/255.0
fun hex2Green(hex: Long) = (hex shr 16 and 0xFF)/255.0
fun hex2Red(hex: Long) = (hex shr 24 and 0xFF)/255.0

fun hex2Hue(hex: Long): Angle {
    val r = hex2Red(hex)
    val g = hex2Green(hex)
    val b = hex2Blue(hex)
    return rgb2Hue(r, g, b)
}

fun rgb2Hue(r: Double, g: Double, b: Double): Angle {
    var x = 0.0
    var y = 0.0
    var c = 0.0
    val max = max(max(g , b), r)
    val min = min(min(g , b), r)

    when {
        r == max -> {
            x = g
            y = b
        }
        g == max -> {
            c = 2.0
            x = b
            y = r
        }
        b == max -> {
            c = 4.0
            x = r
            y = g
        }
    }
    return if (max -min == 0.0) {
        0.degrees
    } else ((c + (x -y)/(max - min))*60).degrees
}

fun rgb2Saturation(r: Double, g: Double, b: Double): Double {
    if ((r == 0.0 && g == 0.0 && b == 0.0)
        || (r == 1.0 && g == 1.0 && b == 1.0))
        return 0.0

    val max = max(max(g , b), r)
    val min = min(min(g , b), r)

    return (max - min)/(1- abs(max + min -1))

}

fun rgb2Lightness(r: Double, g: Double, b: Double): Double {
    val max = max(max(g , b), r)
    val min = min(min(g , b), r)

    return (max + min)/2.0
}

fun hsl(hue: Angle, saturation: Double, lightness: Double, alpha: Double = 1.0): InlineColor {
    return hsl(hue.toDegrees(), saturation, lightness, alpha)
}

fun hsl(hue: Double, saturation: Double, lightness: Double, alpha: Double = 1.0) = InlineColor.hsl(hue, saturation, lightness, alpha)

fun FloatArray.toColor(): InlineColor {
    fun getComponent(i: Int) = if (this.size > i) this[i].toDouble() else 0.0
    val r = getComponent(0)
    val g = getComponent(1)
    val b = getComponent(2)
    val a = getComponent(3)
    return rgba(r, g, b, a)
}

fun Vector4.toColor(): InlineColor {
    val r = this.x
    val g = this.y
    val b = this.z
    val a = this.w
    return rgba(r, g, b, a)
}

operator fun Color.component1() = r
operator fun Color.component2() = g
operator fun Color.component3() = b
operator fun Color.component4() = a