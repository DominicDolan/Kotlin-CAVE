package com.cave.library.vector.vec3

import com.cave.library.angle.Radian
import com.cave.library.tools.CachedDouble
import com.cave.library.tools.CachedRadian
import com.cave.library.tools.hypot
import com.cave.library.vector.vec2.InlineVector
import com.cave.library.vector.vec2.VariableVector2
import com.cave.library.vector.vec2.Vector2
import com.cave.library.vector.vec4.*
import kotlin.math.hypot
import kotlin.math.sqrt

interface Vector3 : Vector2 {
    override val dimension: Int
        get() = 3

    val z: Double

    override val r: Double
        get() = hypot(x, y, z)
    override val theta: Radian
        get() = super.theta

    override fun get(i: Int): Double {
        return when (i) {
            0 -> x
            1 -> y
            2 -> z
            else -> throw Exception("OutOfBoundsException: Tried to call 4th coordinate on a 3D vector")
        }
    }

    override fun getOrZero(i: Int): Double {
        return when (i) {
            0 -> x
            1 -> y
            2 -> z
            else -> 0.0
        }
    }

    companion object {
        fun create(x: Double, y: Double, z: Double) = object : Vector3 {

            private val rCache = CachedDouble.create(arrayOf({ x }, { y }, { z })) { super.r }
            override val r: Double
                get() = rCache.get()

            private val thetaCache = CachedRadian.create(arrayOf({ x }, { y }, { z })) { super.theta }
            override val theta: Radian
                get() = thetaCache.get()

            override val x: Double = x
            override val y: Double = y
            override val z: Double = z

            override fun toString() = "($x, $y, $z)"

        }

        fun from(other: Vector3) = create(other.x, other.y, other.z)

        fun from(xy: Vector2, z: Double = 0.0) = create(xy.x, xy.y, z)

        fun from(xy: InlineVector, z: Double = 0.0) = create(xy.x, xy.y, z)

        fun toString(vector3: Vector3): String {
            val (x, y, z) = vector3
            return "($x, $y, $z)"
        }
    }
}

interface VariableVector3 : Vector3, VariableVector2 {
    override var z: Double

    fun set(x: Double, y: Double, z: Double) {
        this.x = x
        this.y = y
        this.z = z
    }

    fun set(vector: Vector3) = set(vector.x, vector.y, vector.z)

    override fun set(component: Int, value: Double) {
        when(component) {
            0 -> x
            1 -> y
            2 -> z
            else -> throw Exception("OutOfBoundsException: Tried to set 4th coordinate on a 3D vector")
        }
    }

    companion object {
        fun create(x: Double, y: Double, z: Double) = object : VariableVector3 {
            override var x: Double = x
            override var y: Double = y
            override var z: Double = z

            override fun toString() = "($x, $y, $z)"

        }

        fun from(other: Vector3) = create(other.x, other.y, other.z)

        fun from(xy: Vector2, z: Double = 0.0) = create(xy.x, xy.y, z)

        fun from(xy: InlineVector, z: Double = 0.0) = create(xy.x, xy.y, z)
    }
}

operator fun Vector3.component1() = this.x
operator fun Vector3.component2() = this.y
operator fun Vector3.component3() = this.z

fun Vector3.dot(x: Double, y: Double, z: Double) = this.x*x + this.y*y + this.z*z
fun Vector3.dot(other: Vector3) = dot(other.x, other.y, other.z)
