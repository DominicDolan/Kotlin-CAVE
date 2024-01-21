package com.cave.library.color

import com.cave.library.angle.Angle
import com.cave.library.vector.vec4.Vector4
import kotlin.math.max
import kotlin.math.min

interface Color {
    val a: Double
    val r: Double
    val g: Double
    val b: Double

    val hue: Angle
        get() = rgb2Hue(r, g, b)
    val saturation: Double
        get() = rgb2Saturation(r, g, b)
    val lightness: Double
        get() = rgb2Lightness(r, g, b)

    operator fun get(index: Int): Double {
        return when (index) {
            0 -> r
            1 -> g
            2 -> b
            3 -> a
            else -> throw Exception("OutOfBoundsException: Tried to get a fifth component from this color")
        }
    }

    fun getOrZero(index: Int): Double {
        return when (index) {
            0 -> r
            1 -> g
            2 -> b
            3 -> a
            else -> 0.0
        }
    }

    fun toLong(): Long

    companion object {
        fun rgba(r: Double, g: Double, b: Double, a: Double) = object : Color {
            override val a: Double = a
            override val r: Double = r
            override val g: Double = g
            override val b: Double = b

            override fun toLong() = rgba2Hex(r, g, b, a)

            override fun toString(): String {
                return toString(this)
            }
        }

        fun create() = VariableColor.rgba(1.0, 1.0, 1.0, 1.0)

        fun from(color: Color) = rgba(color.r, color.g, color.b, color.a)

        fun hex(hex: Long): Color {
            return from(InlineColor(hex))
        }

        fun hsl(hue: Angle, saturation: Double, lightness: Double, alpha: Double = 1.0): Color {
            return from(InlineColor.hsl(hue, saturation, lightness, alpha))
        }


        val red = rgba(1.0, 0.0, 0.0, 1.0)
        val green = rgba(0.0, 1.0, 0.0, 1.0)
        val blue = rgba(0.0, 0.0, 1.0, 1.0)

        val black = rgba(0.0, 0.0, 0.0, 1.0)
        val white = rgba(1.0, 1.0, 1.0, 1.0)

        fun toString(color: Color): String {
            return "0x" + color.toLong().toString(16).padStart(8, '0')
        }
    }
}

inline class InlineColor(private val hex: Long): Color {
    override val a: Double
        get() = hex2Alpha(hex)
    override val r: Double
        get() = hex2Red(hex)
    override val g: Double
        get() = hex2Green(hex)
    override val b: Double
        get() = hex2Blue(hex)
    override val hue: Angle
        get() = hex2Hue(hex)

    override fun toLong() = hex

    override fun toString(): String {
        return Color.toString(this)
    }

    companion object {

        fun hex(color: Long) = InlineColor(color)

        fun rgba(r: Double, g: Double, b: Double, a: Double) = InlineColor(rgba2Hex(r, g, b, a))

        fun rgba(vector: Vector4) = rgba(vector.x, vector.y, vector.z, vector.w)

        fun create() = rgba(1.0, 1.0, 1.0, 1.0)

        fun hsl(hue: Double, saturation: Double, lightness: Double, alpha: Double = 1.0): InlineColor {
            fun f(n: Int, h: Double, s: Double, l: Double): Double {
                val a = s* min(l, 1.0-l)
                val k = (n + h/30.0)%12
                return (l - a* max(min(min(k-3.0, 9.0-k), 1.0),-1.0))
            }

            val h = (hue + 360.0)%360.0
            val s = saturation
            val l = lightness

            return rgba(f(0, h, s, l), f(8, h, s, l), f(4, h, s, l), alpha)
        }

        fun hsl(hue: Angle, saturation: Double, lightness: Double, alpha: Double = 1.0): InlineColor {
            return hsl(hue.toDegrees(), saturation, lightness, alpha)
        }



    }
}

interface VariableColor : Color {
    override var r: Double
    override var g: Double
    override var b: Double
    override var a: Double

    fun set(red: Double = this.r, green: Double = this.g, blue: Double = this.b, alpha: Double = this.a) {
        r = red
        g = green
        b = blue
        a = alpha
    }

    fun set(color: Color) {
        set(color.r, color.g, color.b, color.a)
    }

    fun set(color: InlineColor) {
        set(color.r, color.g, color.b, color.a)
    }

    fun set(hex: Long) {
        val r = hex2Red(hex)
        val g = hex2Green(hex)
        val b = hex2Blue(hex)
        val a = hex2Alpha(hex)
        set(r, g, b, a)
    }

    override fun toLong(): Long {
        return rgba2Hex(r, g, b, a)
    }

    companion object {
        fun rgba(r: Double, g: Double, b: Double, a: Double): VariableColor {
            return object : VariableColor {
                override var r: Double = r
                override var g: Double = g
                override var b: Double = b
                override var a: Double = a


                override fun toString(): String {
                    return Color.toString(this)
                }
            }
        }

        fun create() = rgba(1.0, 1.0, 1.0, 1.0)

        fun from(color: Color) = rgba(color.r, color.g, color.b, color.a)
        fun from(color: InlineColor) = rgba(color.r, color.g, color.b, color.a)

        fun hex(hex: Long): VariableColor = from(InlineColor(hex))
        fun hsl(hue: Angle, saturation: Double, lightness: Double, alpha: Double = 1.0): VariableColor
                = from(InlineColor.hsl(hue, saturation, lightness, alpha))
    }
}