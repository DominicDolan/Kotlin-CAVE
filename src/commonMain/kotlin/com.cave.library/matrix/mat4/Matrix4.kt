package com.cave.library.matrix.mat4

import com.cave.library.angle.AxisOfRotation
import com.cave.library.angle.VariableAxisOfRotation
import com.cave.library.matrix.ArrayToMatrix
import com.cave.library.matrix.mat3.Matrix3Impl
import com.cave.library.matrix.mat3.StaticMatrix3Impl
import com.cave.library.vector.vec2.InlineVector
import com.cave.library.vector.vec2.Vector2
import com.cave.library.vector.vec3.VariableVector3
import com.cave.library.vector.vec3.Vector3
import com.cave.library.vector.vec4.VariableVector4

interface Matrix4 : StaticMatrix4 {

    override val translation: VariableVector3
    override val scale: VariableVector3
    override val rotation: VariableAxisOfRotation

    override val column: StaticMatrix4.Column
    override val row: StaticMatrix4.Row


    fun identity(): Matrix4
    fun normal(): Matrix4
    fun invert(): Matrix4
    fun transpose(): Matrix4
    fun zero(): Matrix4
    
    fun translate(x: Double, y: Double, z: Double): Matrix4
    
    fun translate(vector: Vector3): Matrix4 {
        return translate(vector.x, vector.y, vector.z)
    }
    
    fun translate(vector: Vector2, z: Double = 0.0): Matrix4 {
        return translate(vector.x, vector.y, z)
    }
    
    fun translate(vector: InlineVector, z: Double = 0.0): Matrix4 {
        return translate(vector.x, vector.y, z)
    }
    
    fun scale(x: Double, y: Double, z: Double): Matrix4

    fun scale(vector: Vector3): Matrix4 {
        return scale(vector.x, vector.y, vector.z)
    }

    fun scale(vector: Vector2, z: Double = 0.0): Matrix4 {
        return scale(vector.x, vector.y, z)
    }

    fun scale(vector: InlineVector, z: Double = 0.0): Matrix4 {
        return scale(vector.x, vector.y, z)
    }

    operator fun set(row: Int, column: Int, value: Double)

    interface Column : StaticMatrix4.Column {
        override fun get(column: Int): VariableVector4
        operator fun set(column: Int, row: Int, value: Double)
        operator fun set(column: Int, value: VariableVector4)

        companion object {
            fun create(array: DoubleArray): Column {
                return VariableColumnImpl(array)
            }
        }
    }
    interface Row : StaticMatrix4.Row {
        override fun get(row: Int): VariableVector4
        operator fun set(row: Int, column: Int, value: Double)
        operator fun set(row: Int, value: VariableVector4)

        companion object {
            fun create(array: DoubleArray): Row {
                return VariableRowImpl(array)
            }
        }
    }

    companion object : Matrix4Creator<Matrix4>() {
        override val size: Int = 4

        override fun create(array: DoubleArray): Matrix4 = Matrix4Impl(array)

    }
}

private class Matrix4Impl(private val array: DoubleArray) : Matrix4, ArrayToMatrix {
    override val size: Int = 4
    private val mat3 by lazy { Matrix3Impl(array, this) }

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

    override fun translate(x: Double, y: Double, z: Double): Matrix4 {
        TODO("Not yet implemented")
    }

    override fun scale(x: Double, y: Double, z: Double): Matrix4 {
        array.scale(x, y, z)
        return this
    }

    override fun set(row: Int, column: Int, value: Double) {
        array[column, row] = value
    }

    override fun get(column: Int, row: Int) = array[column, row]

    override val translation: VariableVector3
        get() = TODO("Not yet implemented")
    override val scale: VariableVector3
        get() = TODO("Not yet implemented")
    override val rotation: VariableAxisOfRotation
        get() = TODO("Not yet implemented")
    override val column: StaticMatrix4.Column
        get() = TODO("Not yet implemented")
    override val row: StaticMatrix4.Row
        get() = TODO("Not yet implemented")

    override fun fill(array: DoubleArray) {
        TODO("Not yet implemented")
    }


}

private class VariableColumnImpl(private val array: DoubleArray)
    : Matrix4.Column, ArrayToMatrix by Matrix4 {
    private val columns = Array<VariableVector4>(4) { ColumnVariableVector4(it, array, Matrix4) }

    override fun set(column: Int, row: Int, value: Double) {
        array[column, row] = value
    }

    override fun set(column: Int, value: VariableVector4) {
        columns[column].set(value)
    }

    override fun get(column: Int, row: Int) = array[column, row]

    override fun get(column: Int): VariableVector4 {
        return columns[column]
    }
}

private class VariableRowImpl(private val array: DoubleArray)
    : Matrix4.Row, ArrayToMatrix by Matrix4 {
    private val rows = Array<VariableVector4>(4) { ColumnVariableVector4(it, array, Matrix4) }

    override fun set(row: Int, column: Int, value: Double) {
        array[column, row] = value
    }

    override fun set(row: Int, value: VariableVector4) {
        rows[row].set(value)
    }

    override fun get(row: Int, column: Int) = array[column, row]

    override fun get(row: Int): VariableVector4 {
        return rows[row]
    }
}
