package com.cave.library.vector.vec4

import com.cave.library.vector.vec2.Vector2i
import com.cave.library.vector.vec3.VariableVector3i
import com.cave.library.vector.vec3.Vector3i

interface Vector4i : Vector3i{
    val w: Int

    override fun get(i: Int): Int {
        return when (i) {
            0 -> x
            1 -> y
            2 -> z
            3 -> w
            else -> throw Exception("OutOfBoundsException: Tried to call 5th coordinate on a 4D vector")
        }
    }

    override fun getOrZero(i: Int): Int {
        return when (i) {
            0 -> x
            1 -> y
            2 -> z
            3 -> w
            else -> 0
        }
    }

    companion object {
        fun create(x: Int, y: Int, z: Int, w: Int) = object : Vector4i {
            override val x: Int = x
            override val y: Int = y
            override val z: Int = z
            override val w: Int = z

            override fun toString() = "($x, $y, $z, $w)"

        }

        fun from(other: Vector4i) = create(other.x, other.y, other.z, other.w)

        fun from(xy: Vector2i, z: Int = 0, w: Int = 0) = create(xy.x, xy.y, z, w)

        fun from(xyz: Vector3i, w: Int = 0) = create(xyz.x, xyz.y, xyz.z, w)

    }
}


interface VariableVector4i : Vector4i, VariableVector3i {
    override var z: Int

    companion object {
        fun create(x: Int, y: Int, z: Int, w: Int) = object : VariableVector4i {
            override var x: Int = x
            override var y: Int = y
            override var z: Int = z
            override var w: Int = w

            override fun toString() = "($x, $y, $z)"

        }

        fun from(other: Vector4i) = create(other.x, other.y, other.z, other.w)

        fun from(xy: Vector2i, z: Int = 0, w: Int = 0) = create(xy.x, xy.y, z, w)

        fun from(xyz: Vector3i, w: Int = 0) = create(xyz.x, xyz.y, xyz.z, w)

    }
}

operator fun Vector4i.component1() = this.x
operator fun Vector4i.component2() = this.y
operator fun Vector4i.component3() = this.z
operator fun Vector4i.component4() = this.w