package com.cave.library.matrix.mat4

import com.cave.library.angle.Degree
import com.cave.library.angle.Radian
import com.cave.library.angle.radians
import com.cave.library.matrix.MatrixArrayTransforms
import com.cave.library.tools.hypot
import com.cave.library.vector.timesAssign
import com.cave.library.vector.vec3.MutableVector3
import com.cave.library.vector.vec3.Vector3
import com.cave.library.vector.vec4.MutableVector4
import com.cave.library.vector.vec4.Vector4
import kotlin.math.abs
import kotlin.math.atan
import kotlin.math.tan

interface Matrix4 {

    val translation: MatrixVector3
    val scale: MatrixVector3
    val rotation: MatrixRotationVector
    val skew: MatrixAngleVector2

    val column: Columns
    val row: Rows
    
    fun fill(array: DoubleArray)
    fun fill(array: FloatArray)

    fun identity(): Matrix4
    fun normal(): Matrix4
    fun invert(): Matrix4
    fun transpose(): Matrix4
    fun zero(): Matrix4

    fun perspective(fov: Degree, aspectRatio: Double, near: Double = 0.0, far: Double = Double.POSITIVE_INFINITY): Matrix4
    fun perspective(fov: Radian, aspectRatio: Double, near: Double = 0.0, far: Double = Double.POSITIVE_INFINITY): Matrix4

    operator fun set(row: Int, column: Int, value: Double)
    operator fun get(row: Int, column: Int): Double

    fun set(other: Matrix4)

    operator fun timesAssign(other: Matrix4)

    interface Columns {
        operator fun get(column: Int): MutableVector4
        operator fun set(column: Int, value: MutableVector4)
    }

    interface Rows {
        operator fun get(row: Int): MutableVector4
        operator fun set(row: Int, value: MutableVector4)
    }

    companion object : Matrix4Factory<Matrix4>() {
        override val columnCount: Int = 4
        override val rowCount: Int = 4

        override fun create(array: DoubleArray): Matrix4 = Matrix4Impl(array)

        fun toString(matrix: Matrix4): String {
            return matrix.row[0].toString() + '\n' +
                    matrix.row[1].toString() + '\n' +
                    matrix.row[2].toString() + '\n' +
                    matrix.row[3].toString() + '\n'
        }

    }
}

private class Matrix4Impl(val array: DoubleArray) : Matrix4, MatrixArrayTransforms {
    override val columnCount: Int = 4
    override val rowCount: Int = 4

    override fun identity(): Matrix4 {
        array.identity()
        return this
    }

    override fun normal(): Matrix4 {
        TODO("Not yet implemented")
    }

    override fun invert(): Matrix4 {
        TODO("Not yet implemented")
    }

    override fun transpose(): Matrix4 {
        TODO("Not yet implemented")
    }

    override fun zero(): Matrix4 {
        array.zero()
        return this
    }

    override fun perspective(fov: Degree, aspectRatio: Double, near: Double, far: Double): Matrix4 {
        array.perspective(fov.toRadians(), aspectRatio, near, far)
        return this
    }

    override fun perspective(fov: Radian, aspectRatio: Double, near: Double, far: Double): Matrix4 {
        array.perspective(fov, aspectRatio, near, far)
        return this
    }

    override fun set(other: Matrix4) {
        other.fill(array)
    }

    override fun set(row: Int, column: Int, value: Double) {
        array[row, column] = value
    }

    override fun timesAssign(other: Matrix4) {
        array.product(this, other)
    }

    override fun get(row: Int, column: Int) = array[row, column]

    override val translation: MatrixVector3 by lazy {
        object : MatrixVector3 {
            override val defaultApply = Vector3.create(0.0, 0.0, 0.0)

            override fun apply(x: Double, y: Double, z: Double): Matrix4 {
                array.applyTranslation(this@Matrix4Impl, x, y, z)
                return this@Matrix4Impl
            }

            override var x: Double
                get() = array[0, 3]
                set(value) { array[0, 3] = value }
            override var y: Double
                get() = array[1, 3]
                set(value) { array[1, 3] = value }
            override var z: Double
                get() = array[2, 3]
                set(value) { array[2, 3] = value }

        }
    }

    override val scale: MatrixVector3 by lazy {
        object : MatrixVector3 {
            override val defaultApply = Vector3.create(1.0, 1.0, 1.0)

            override fun apply(x: Double, y: Double, z: Double): Matrix4 {
                column[0] *= x
                column[1] *= y
                column[2] *= z
                return this@Matrix4Impl
            }

            override var x: Double
                get() = hypot(column[0] as Vector3)
                set(value) {
                    array[0, 0] = value
                }
            override var y: Double
                get() = hypot(column[1] as Vector3)
                set(value) {
                    array[1, 1] = value
                }
            override var z: Double
                get() = hypot(column[2] as Vector3)
                set(value) {
                    array[2, 2] = value
                }
        }
    }

    override val rotation: MatrixRotationVector = object : MatrixRotationVector {

        override var angle: Radian
            get() = array.angle
            set(value) {
                array.rotate(value, axis.x, axis.y, axis.z)
            }

        override fun set(angle: Radian, x: Double, y: Double, z: Double) {
            array.rotate(angle, x, y, z)
        }

        // TODO: Theses apply functions could be made more efficient
        override fun apply(angle: Radian, x: Double, y: Double, z: Double): Matrix4 {
            array.applyRotation(angle.toRadians(), x, y, z)
            return this@Matrix4Impl
        }

        override fun apply(angle: Degree, x: Double, y: Double, z: Double) = apply(angle.toRadians(), x, y, z)

        override fun apply(angle: Radian) = apply(angle, axis.x, axis.y, axis.z)

        override fun apply(angle: Degree) = apply(angle.toRadians())

        private val defaultAxis = object : MutableVector3 {
            override var x: Double = 0.0
            override var y: Double = 0.0
            override var z: Double = 1.0
        }

        private val fullAxis = object : Vector3 {
            override val x: Double
                get() = abs(array[1, 2] - array[2, 1])
            override val y: Double
                get() = abs(array[2, 0] - array[0, 2])
            override val z: Double
                get() = abs(array[0, 1] - array[1, 0])
        }

        override val axis: MutableVector3 = object : MutableVector3 {
            override var x: Double
                get() = getAxisComponent(fullAxis.x, defaultAxis.x)
                set(value) {
                    defaultAxis.x = value
                    array.rotate(angle, value, y, z)
                }
            override var y: Double
                get() = getAxisComponent(fullAxis.y, defaultAxis.y)
                set(value) {
                    defaultAxis.y = value
                    array.rotate(angle, x, value, z)
                }
            override var z: Double
                get() = getAxisComponent(fullAxis.z, defaultAxis.z)
                set(value) {
                    defaultAxis.z = value
                    array.rotate(angle, x, y, value)
                }

            override fun set(x: Double, y: Double, z: Double) {
                defaultAxis.set(x, y, z)
                array.rotate(angle, x, y, z)
            }

            override fun set(x: Double, y: Double) {
                defaultAxis.set(x, y, 0.0)
                array.rotate(angle, x, y, z)
            }

            override fun toString() = Vector3.toString(this)

            private fun getAxisComponent(component: Double, default: Double): Double {
                val (x, y, z) = fullAxis
                val r2 = x*x + y*y + z*z
                return if (r2 < 1.0E-6 || !r.isFinite()) default
                       else component/r

            }

        }
    }

    override val skew: MatrixAngleVector2 = object : MatrixAngleVector2 {
        override fun apply(x: Radian, y: Radian): Matrix4 {
            array.applySkew(this@Matrix4Impl, x, y)
            return this@Matrix4Impl
        }

        override var x: Radian
            get() = (-atan(array[0, 1])).radians
            set(value) {
                array[0, 1] = tan(-(value.toDouble()))
            }
        override var y: Radian
            get() = atan(array[1, 0]).radians
            set(value) {
                array[1, 0] = tan(value.toDouble())
            }

    }

    override val column: Matrix4.Columns  = object : Matrix4.Columns {
        private val columns = Array<MutableVector4>(4) { object : MutableVector4 {
            override var x: Double
                get() = array[0, it]
                set(value) { array[0, it] = value}
            override var y: Double
                get() = array[1, it]
                set(value) { array[1, it] = value}
            override var z: Double
                get() = array[2, it]
                set(value) {array[2, it] = value}
            override var w: Double
                get() = array[3, it]
                set(value) { array[3, it] = value }

        } }

        override fun get(column: Int) = columns[column]
        override fun set(column: Int, value: MutableVector4) {
            columns[column].set(value)
        }
    }
    
    override val row: Matrix4.Rows = object : Matrix4.Rows {
        private val rows = Array<MutableVector4>(4) { object : MutableVector4 {
            override var x: Double
                get() = array[it, 0]
                set(value) { array[it, 0] = value}
            override var y: Double
                get() = array[it, 1]
                set(value) { array[it, 1] = value}
            override var z: Double
                get() = array[it, 2]
                set(value) {array[it, 2] = value}
            override var w: Double
                get() = array[it, 3]
                set(value) { array[it, 3] = value }

            override fun toString(): String {
                return Vector4.toString(this)
            }
        } }

        override fun get(row: Int) = rows[row]
        override fun set(row: Int, value: MutableVector4) {
            rows[row].set(value)
        }
    }

    override fun fill(array: DoubleArray) {
        this.array.copyInto(array)
    }

    override fun fill(array: FloatArray) {
        this.array.copyInto(array)
    }

    override fun toString(): String {
        return row[0].toString() + '\n' +
                row[1].toString() + '\n' +
                row[2].toString() + '\n' +
                row[3].toString()
    }

}

