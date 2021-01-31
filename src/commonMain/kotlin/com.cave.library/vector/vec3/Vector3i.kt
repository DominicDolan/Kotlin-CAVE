package com.cave.library.vector.vec3

import com.cave.library.vector.vec2.*

interface Vector3i : Vector2i {
    val z: Int


    override fun get(i: Int): Int {
        return when (i) {
            0 -> x
            1 -> y
            2 -> z
            else -> throw Exception("OutOfBoundsException: Tried to call 4th coordinate on a 3D vector")
        }
    }

    override fun getOrZero(i: Int): Int {
        return when (i) {
            0 -> x
            1 -> y
            2 -> z
            else -> 0
        }
    }

    companion object {
        fun create(x: Int, y: Int, z: Int) = object : Vector3i {
            override val x: Int = x
            override val y: Int = y
            override val z: Int = z

            override fun toString() = "($x, $y, $z)"

        }

        fun from(other: Vector3i) = create(other.x, other.y, other.z)

        fun from(xy: Vector2i, z: Int = 0) = create(xy.x, xy.y, z)

    }
}


interface VariableVector3i : Vector3i, VariableVector2i {
    override var z: Int

    companion object {
        fun create(x: Int, y: Int, z: Int) = object : VariableVector3i {
            override var x: Int = x
            override var y: Int = y
            override var z: Int = z

            override fun toString() = "($x, $y, $z)"

        }

        fun from(other: Vector3i) = create(other.x, other.y, other.z)

        fun from(xy: Vector2i, z: Int = 0) = create(xy.x, xy.y, z)

    }
}

operator fun Vector3i.component1() = this.x
operator fun Vector3i.component2() = this.y
operator fun Vector3i.component3() = this.z