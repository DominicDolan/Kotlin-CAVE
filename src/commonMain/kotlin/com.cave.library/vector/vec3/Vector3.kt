package com.cave.library.vector.vec3

import com.cave.library.angle.Radian
import com.cave.library.vector.vec2.InlineVector
import com.cave.library.vector.vec2.VariableVector2
import com.cave.library.vector.vec2.Vector2
import kotlin.math.hypot

interface Vector3 : Vector2 {
    val z: Double

    override val r: Double
        get() = hypot(super.r, z)
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
            override val x: Double = x
            override val y: Double = y
            override val z: Double = z

            override fun toString() = "($x, $y, $z)"

        }

        fun from(other: Vector3) = create(other.x, other.y, other.z)

        fun from(xy: Vector2, z: Double = 0.0) = create(xy.x, xy.y, z)

        fun from(xy: InlineVector, z: Double = 0.0) = create(xy.x, xy.y, z)
    }
}

interface VariableVector3 : Vector3, VariableVector2 {
    override var z: Double

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
