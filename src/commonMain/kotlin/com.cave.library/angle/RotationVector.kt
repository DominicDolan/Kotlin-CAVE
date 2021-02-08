package com.cave.library.angle

import com.cave.library.vector.vec3.VariableVector3
import com.cave.library.vector.vec3.Vector3

interface AxisOfRotation : Vector3 {
    val angle: Radian
}

interface RotationAxisDeg : Vector3 {
    val angle: Degree
}

interface VariableAxisOfRotation : VariableVector3, AxisOfRotation {
    override var angle: Radian

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