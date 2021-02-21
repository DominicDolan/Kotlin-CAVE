package com.cave.library.angle

import com.cave.library.matrix.mat3.StaticMatrix3
import com.cave.library.tools.hypot
import com.cave.library.vector.vec3.VariableVector3
import com.cave.library.vector.vec3.Vector3
import kotlin.math.acos
import kotlin.math.sqrt

interface AxisOfRotation : Vector3 {
    val rotation: Radian

    companion object {
        fun create(rotation: Angle, x: Double, y: Double, z: Double): AxisOfRotation {
            val r = hypot(x, y, z)
            val isUnit =  (r > 0.99 && r < 1.01)

            val adjustedX = if (isUnit) x else x/r
            val adjustedY = if (isUnit) y else y/r
            val adjustedZ = if (isUnit) z else z/r

            return object : AxisOfRotation {
                override val rotation: Radian = rotation.toRadians()
                override val x: Double = adjustedX; override val y: Double = adjustedY; override val z: Double = adjustedZ

                override fun toString(): String = Vector3.toString(this) + ", rotation: ${this.rotation}"
            }
        }
    }
}


internal abstract class AbstractAxisOfRotation : AxisOfRotation {
    protected abstract val matrix: StaticMatrix3

    private val s: Double
        get() {
            val m1221 = (m(1,2) - m(2,1))
            val m2002 = (m(2,0) - m(0,2))
            val m0110 = (m(0,1) - m(1,0))

            val sSquared = m1221*m1221 + m2002*m2002 + m0110*m0110

            return if (sSquared == 0.0) 0.0
            else sqrt(sSquared)
        }

    override val rotation: Radian
        get() = acos((m(0,0) + m(1, 1) + m(2, 2) - 1) / 2).radians
    override val x: Double
        get() = (m(1,2) - m(2,1)) / s
    override val y: Double
        get() = (m(2,0) - m(0,2)) / s
    override val z: Double
        get() = (m(0,1) - m(1,0))/ s

    private fun m(c: Int, r: Int): Double {
        return matrix.row[r].normalized[c]
    }

    override fun toString() = "rotation: $rotation, axis: ${Vector3.toString(this)}"

}

interface RotationAxisDeg : Vector3 {
    val angle: Degree
}

interface VariableAxisOfRotation : VariableVector3, AxisOfRotation {
    override var rotation: Radian

    fun set(angle: Radian, x: Double, y: Double, z: Double)
    fun set(angle: Degree, x: Double, y: Double, z: Double) = set(angle.toRadians(), x, y, z)
}

interface VariableRotationAxisDeg : VariableVector3, RotationAxisDeg {
    override var angle: Degree
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