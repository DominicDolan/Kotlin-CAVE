package com.cave.library.angle

import com.cave.library.matrix.MatrixContext
import com.cave.library.matrix.mat3.MatrixVector3
import com.cave.library.tools.hypot
import com.cave.library.vector.vec3.VariableVector3
import com.cave.library.vector.vec3.Vector3
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


internal abstract class AbstractRotation(private val array: DoubleArray, private val context: MatrixContext) : Rotation, MatrixContext by context {

    override val angle: Radian
        get() = acos((array[0, 0] + array[1, 1] + array[2, 2] - 1) / 2).radians

    val nonNormalAxis = Axis()
    override val axis: Vector3
        get() {
            return nonNormalAxis.normalized
        }

    override fun toString() = Rotation.toString(this) + " r: ${nonNormalAxis.r}"

    inner class Axis : MatrixVector3(array,
        xGetter = { it[1, 2] - it[2, 1] },
        yGetter = { it[2, 0] - it[0, 2] },
        zGetter = { it[0, 1] - it[1, 0] },
        context
    )

}


interface VariableRotation : Rotation {
    override var angle: Radian

    override val axis: VariableVector3

    fun set(angle: Radian, x: Double, y: Double, z: Double)
    fun set(angle: Degree, x: Double, y: Double, z: Double) = set(angle.toRadians(), x, y, z)
}

interface RotationVector {
    val rx: Angle
    val ry: Angle
    val rz: Angle

    companion object {
        fun create(rx: Degree, ry: Degree, rz: Degree) = object : RotationVectorDeg {
            override val rx: Degree = rx
            override val ry: Degree = ry
            override val rz: Degree = rz

        }

        fun create(rx: Radian, ry: Radian, rz: Radian) = object : RotationVectorRad {
            override val rx: Radian = rx
            override val ry: Radian = ry
            override val rz: Radian = rz
        }

        fun createVar(rx: Degree, ry: Degree, rz: Degree) = VariableRotationVector.create(rx, ry, rz)
        fun createVar(rx: Radian, ry: Radian, rz: Radian) = VariableRotationVector.create(rx, ry, rz)

        fun from(rotation: RotationVector) = object : RotationVector {
            override val rx: Angle = rotation.rx
            override val ry: Angle = rotation.ry
            override val rz: Angle = rotation.rz
        }

        fun from(rotation: RotationVectorDeg) = create(rotation.rx, rotation.ry, rotation.rz)
        fun from(rotation: RotationVectorRad) = create(rotation.rx, rotation.ry, rotation.rz)
    }
}

interface RotationVectorDeg : RotationVector {
    override val rx: Degree
    override val ry: Degree
    override val rz: Degree
}

interface RotationVectorRad : RotationVector {
    override val rx: Radian
    override val ry: Radian
    override val rz: Radian
}

interface VariableRotationVector : RotationVector {
    override val rx: Angle
    override val ry: Angle
    override val rz: Angle

    fun set(rx: Degree, ry: Degree, rz: Degree)
    fun set(rx: Radian, ry: Radian, rz: Radian)

    fun set(rotation: VariableRotationVectorDeg) {
        set(rotation.rx, rotation.ry, rotation.rz)
    }

    fun set(rotation: VariableRotationVectorRad) {
        set(rotation.rx, rotation.ry, rotation.rz)
    }

    companion object {
        fun create(rx: Degree, ry: Degree, rz: Degree) = object : VariableRotationVectorDeg {
            override var rx: Degree = rx
            override var ry: Degree = ry
            override var rz: Degree = rz

        }

        fun create(rx: Radian, ry: Radian, rz: Radian) = object : VariableRotationVectorRad {
            override var rx: Radian = rx
            override var ry: Radian = ry
            override var rz: Radian = rz

        }
    }
}

interface VariableRotationVectorDeg : VariableRotationVector, RotationVectorDeg {
    override var rx: Degree
    override var ry: Degree
    override var rz: Degree

    override fun set(rx: Degree, ry: Degree, rz: Degree) {
        this.rx = rx
        this.ry = ry
        this.rz = rx
    }

    override fun set(rx: Radian, ry: Radian, rz: Radian) {
        this.rx = rx.toDegrees()
        this.ry = ry.toDegrees()
        this.rz = rx.toDegrees()
    }

}

interface VariableRotationVectorRad : VariableRotationVector, RotationVectorRad {
    override var rx: Radian
    override var ry: Radian
    override var rz: Radian

    override fun set(rx: Degree, ry: Degree, rz: Degree) {
        this.rx = rx.toRadians()
        this.ry = ry.toRadians()
        this.rz = rx.toRadians()
    }
    override fun set(rx: Radian, ry: Radian, rz: Radian) {
        this.rx = rx
        this.ry = ry
        this.rz = rx
    }

}