package com.cave.library.angle

import com.cave.library.matrix.MatrixArrayTransforms
import com.cave.library.matrix.mat3.MatrixVector3
import com.cave.library.tools.hypot
import com.cave.library.vector.vec3.VariableVector3
import com.cave.library.vector.vec3.Vector3
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.acos

interface Rotation : Angle {
    val angle: Radian
    val axis: Vector3

    override fun toDouble() = angle.toDouble()

    override fun toFloat() = angle.toFloat()

    override fun toDegrees() = angle.toDegrees()

    override fun toRadians() = angle

    companion object {
        fun create(rotation: Angle, x: Double, y: Double, z: Double): Rotation {
            val r = hypot(x, y, z)
            val isUnit =  (r > 0.99 && r < 1.01)

            val adjustedX = if (isUnit) x else x/r
            val adjustedY = if (isUnit) y else y/r
            val adjustedZ = if (isUnit) z else z/r

            return object : Rotation {
                override val angle: Radian = rotation.toRadians()
                override val axis: Vector3 = Vector3.create(adjustedX, adjustedY, adjustedZ)

                override fun toString(): String = Companion.toString(this)
            }
        }

        fun aboutZ(rotation: Angle) = create(rotation, 0.0, 0.0, 1.0)

        fun toString(rotation: Rotation): String {
            return "angle: ${rotation.angle}, axis: ${Vector3.toString(rotation.axis)}"
        }
    }
}


internal abstract class AbstractRotation(private val array: DoubleArray, private val transforms: MatrixArrayTransforms) : Rotation, MatrixArrayTransforms by transforms {

    override val angle: Radian
        get() {

            val trace = array[0, 0] + array[1, 1] + array[2, 2]
            val angle = safeAcos((trace - 1.0) / 2.0)

            return if (axisX >= 0 && axisY >= 0 && axisZ >=0) angle.radians
            else (2*PI - angle).radians
        }

    private fun safeAcos(v: Double): Double {
        return if (v < -1.0) PI else if (v > +1.0) 0.0 else acos(v)
    }
    private val nonNormalAxis = Axis()
    override val axis: Vector3
        get() {
            return nonNormalAxis.normalized
        }

    private val axisX: Double
        get() = array[1, 2] - array[2, 1]
    private val axisY: Double
        get() = array[2, 0] - array[0, 2]
    private val axisZ: Double
        get() = array[0, 1] - array[1, 0]

    inner class Axis : MatrixVector3(array,
        xGetter = { abs(axisX) },
        yGetter = { abs(axisY) },
        zGetter = { abs(axisZ) },
        transforms
    )

    override fun toString() = Rotation.toString(this)
}


interface VariableRotation : Rotation {
    override var angle: Radian

    override val axis: VariableVector3

    fun set(angle: Radian, x: Double, y: Double, z: Double)
    fun set(angle: Degree, x: Double, y: Double, z: Double) = set(angle.toRadians(), x, y, z)
}
