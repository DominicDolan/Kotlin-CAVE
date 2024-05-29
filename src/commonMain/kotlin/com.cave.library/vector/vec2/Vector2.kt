package com.cave.library.vector.vec2

import com.cave.library.angle.Angle
import com.cave.library.angle.radians
import com.cave.library.tools.CachedDouble
import com.cave.library.tools.CachedRadian
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.hypot
import kotlin.math.sin

interface Vector2 {
    val dimension: Int
        get() = 2

    val x: Double
    val y: Double

    val r get() = hypot(x, y)

    val theta get() = atan2(this.y, this.x).radians

    operator fun get(i: Int): Double {
        return if (i == 0) x else if (i == 1) y else throw Exception("OutOfBoundsException: Tried to call 3rd coordinate on a 2D vector")
    }

    fun getOrZero(i: Int): Double {
        return if (i == 0) x else if (i == 1) y else 0.0
    }

    operator fun component1() = x
    operator fun component2() = y

    companion object {
        val origin = create()
        fun create(x: Double, y: Double) = object : Vector2 {
            override val r: Double = super.r

            override val x: Double = x
            override val y: Double = y

            override fun toString() = toString(this)
        }

        fun create(other: Vector2) = create(other.x, other.y)
        fun create(other: InlineVector) = create(other.x, other.y)
        fun create() = create(0.0, 0.0)

        fun toString(vector: Vector2) = "(${vector.x}, ${vector.y})"
    }
}

interface MutableVector2 : Vector2 {
    override var x: Double
    override var y: Double

    fun set(x: Double, y: Double) {
        this.x = x
        this.y = y
    }

    fun set(vector: Vector2) = set(vector.x, vector.y)
    fun set(vector: InlineVector) = set(vector.x, vector.y)
    fun set(r: Number, theta: Angle) = set(r.toDouble()*cos(theta.toRadians()), r.toDouble()*sin(theta.toRadians()))

    operator fun set(component: Int, value: Double) {
        when(component) {
            0 -> x = value
            1 -> y = value
            else -> throw Exception("OutOfBoundsException: Tried to set 3rd coordinate on a 2D vector")
        }
    }

    /**
     * Scale all components of this vector by the specified value given by [scale]
     *
     */
    fun scale(scale: Double) = scaleXY(scale)

    /**
     * Scale the x and y components of this vector by the specified value given by [scale]
     *
     */
    fun scaleXY(scale: Double) {
        this.x *= scale
        this.y *= scale
    }

    companion object {
        fun create(x: Double, y: Double) = object : MutableVector2 {

            private val rCache = CachedDouble.create(arrayOf({ this.x }, { this.y })) { super.r }
            override val r: Double
                get() = rCache.get()

            private val thetaCache = CachedRadian.create(arrayOf({ this.x }, { this.y })) { super.theta }
            override val theta: Angle
                get() = thetaCache.get()

            override var x: Double = x
            override var y: Double = y

            override fun toString() = Vector2.toString(this)
        }

        fun create() = create(0.0, 0.0)
        fun create(other: Vector2) = create(other.x, other.y)
        fun create(other: InlineVector) = create(other.x, other.y)
    }
}

