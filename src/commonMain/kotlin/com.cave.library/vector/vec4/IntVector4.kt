package com.cave.library.vector.vec4

import com.cave.library.vector.vec2.IntVector2
import com.cave.library.vector.vec3.IntVector3
import com.cave.library.vector.vec3.VariableIntVector3

interface IntVector4 : IntVector3{
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
        fun create(x: Int, y: Int, z: Int, w: Int) = object : IntVector4 {
            override val x: Int = x
            override val y: Int = y
            override val z: Int = z
            override val w: Int = z

            override fun toString() = "($x, $y, $z, $w)"

        }

        fun from(other: IntVector4) = create(other.x, other.y, other.z, other.w)

        fun from(xy: IntVector2, z: Int = 0, w: Int = 0) = create(xy.x, xy.y, z, w)

        fun from(xyz: IntVector3, w: Int = 0) = create(xyz.x, xyz.y, xyz.z, w)

    }
}


interface VariableIntVector4 : IntVector4, VariableIntVector3 {
    override var z: Int

    companion object {
        fun create(x: Int, y: Int, z: Int, w: Int) = object : VariableIntVector4 {
            override var x: Int = x
            override var y: Int = y
            override var z: Int = z
            override var w: Int = w

            override fun toString() = "($x, $y, $z)"

        }

        fun from(other: IntVector4) = create(other.x, other.y, other.z, other.w)

        fun from(xy: IntVector2, z: Int = 0, w: Int = 0) = create(xy.x, xy.y, z, w)

        fun from(xyz: IntVector3, w: Int = 0) = create(xyz.x, xyz.y, xyz.z, w)

    }
}

operator fun IntVector4.component1() = this.x
operator fun IntVector4.component2() = this.y
operator fun IntVector4.component3() = this.z
operator fun IntVector4.component4() = this.w