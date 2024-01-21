package com.cave.library.vector.vec4

import com.cave.library.angle.Angle
import com.cave.library.tools.CachedDouble
import com.cave.library.tools.CachedRadian
import com.cave.library.tools.hypot
import com.cave.library.vector.vec2.InlineVector
import com.cave.library.vector.vec2.Vector2
import com.cave.library.vector.vec3.MutableVector3
import com.cave.library.vector.vec3.Vector3

interface Vector4 : Vector3 {
    override val dimension: Int
        get() = 4

    val w: Double

    override val r: Double
        get() = hypot(x, y, z, w)
    override val theta: Angle
        get() = super.theta

    override fun get(i: Int): Double {
        return when (i) {
            0 -> x
            1 -> y
            2 -> z
            3 -> w
            else -> throw Exception("OutOfBoundsException: Tried to call coordinate $i on a ${dimension}D vector")
        }
    }

    override fun getOrZero(i: Int): Double {
        return when (i) {
            0 -> x
            1 -> y
            2 -> z
            3 -> w
            else -> 0.0
        }
    }

    operator fun component4() = w

    companion object {
        fun create(x: Double, y: Double, z: Double, w: Double) = object : Vector4 {
            override val x: Double = x
            override val y: Double = y
            override val z: Double = z
            override val w: Double = w

            override fun toString() = toString(this)

        }

        fun create() = create(0.0, 0.0, 0.0, 0.0)

        fun from(other: Vector4) = create(other.x, other.y, other.z, other.w)

        fun from(xy: Vector2, z: Double = 0.0, w: Double = 0.0) = create(xy.x, xy.y, z, w)

        fun from(xyz: Vector3, w: Double = 0.0) = create(xyz.x, xyz.y, xyz.z, w)

        fun from(xy: InlineVector, z: Double = 0.0, w: Double = 0.0) = create(xy.x, xy.y, z, w)

        fun toString(vector4: Vector4): String {
            val (x, y, z, w) = vector4
            return "($x, $y, $z, $w)"
        }
    }
}

interface MutableVector4 : Vector4, MutableVector3 {
    override var w: Double

    fun set(x: Double, y: Double, z: Double, w: Double) {
        this.x = x
        this.y = y
        this.z = z
        this.w = w
    }

    fun set(vector: Vector4) = set(vector.x, vector.y, vector.z, vector.w)

    override fun set(component: Int, value: Double) {
        when(component) {
            0 -> x = value
            1 -> y = value
            2 -> z = value
            3 -> w = value
            else -> throw Exception("OutOfBoundsException: Tried to set 5th coordinate on a 4D vector")
        }
    }

    override fun scale(scale: Double) {
        this.scaleXYZW(scale)
    }

    /**
     * Scale the x, y, z and w components of this vector by the specified value given by [scale]
     *
     */
    fun scaleXYZW(scale: Double) {
        this.x *= scale
        this.y *= scale
        this.z *= scale
        this.w *= scale
    }

    companion object {
        fun create(x: Double, y: Double, z: Double, w: Double) = object : MutableVector4 {

            private val rCache = CachedDouble.create(arrayOf({ x }, { y }, { z })) { super.r }
            override val r: Double
                get() = rCache.get()

            private val thetaCache = CachedRadian.create(arrayOf({ x }, { y }, { z })) { super.theta }
            override val theta: Angle
                get() = thetaCache.get()

            override var x: Double = x
            override var y: Double = y
            override var z: Double = z
            override var w: Double = w

            override fun toString() = "($x, $y, $z)"

        }

        fun create() = create(0.0, 0.0, 0.0, 0.0)

        fun from(other: Vector4) = create(other.x, other.y, other.z, other.w)

        fun from(xy: Vector2, z: Double = 0.0, w: Double = 0.0) = create(xy.x, xy.y, z, w)

        fun from(xyz: Vector3, w: Double = 0.0) = create(xyz.x, xyz.y, xyz.z, w)

        fun from(xy: InlineVector, z: Double = 0.0, w: Double = 0.0) = create(xy.x, xy.y, z, w)
    }
}