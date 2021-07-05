package com.cave.library.matrix.mat4

import com.cave.library.angle.radians
import com.cave.library.matrix.joml.createMatrix4f
import com.cave.library.matrix.joml.toDoubleArray
import com.cave.library.testUtils.areWithinError
import com.cave.library.testUtils.contentEquals
import com.cave.library.testUtils.createIdentifyableMatrix4DoubleArray
import com.cave.library.testUtils.findUnequal
import com.cave.library.vector.dot
import com.cave.library.vector.vec3.Vector3
import org.joml.Matrix4f
import org.joml.Vector3f
import org.junit.Test
import kotlin.test.fail

class Matrix4CaveVsJomlTests {
    @Test
    fun jomlAndCaveReturnTheSameArray() {

        val expected = createMatrix4f(createIdentifyableMatrix4DoubleArray()).toDoubleArray()

        val matrix = Matrix4.from(createIdentifyableMatrix4DoubleArray())

        val actual = DoubleArray(16)

        matrix.fill(actual)

        assert(actual.contentEquals(expected, 0.01)) {
            "expected: ${expected.contentToString()}\n" +
            "actual:   ${actual.contentToString()}\n"

        }
    }

    @Test
    fun caveUsesSameCoordinatesAsJoml() {
        val cave = Matrix4.from(createIdentifyableMatrix4DoubleArray())
        val joml = createMatrix4f(createIdentifyableMatrix4DoubleArray())

        assertAreWithinError(joml.m00().toDouble(), cave[0, 0], 0.001)
        assertAreWithinError(joml.m01().toDouble(), cave[1, 0], 0.001)
        assertAreWithinError(joml.m02().toDouble(), cave[2, 0], 0.001)
        assertAreWithinError(joml.m03().toDouble(), cave[3, 0], 0.001)

        assertAreWithinError(joml.m10().toDouble(), cave[0, 1], 0.001)
        assertAreWithinError(joml.m11().toDouble(), cave[1, 1], 0.001)
        assertAreWithinError(joml.m12().toDouble(), cave[2, 1], 0.001)
        assertAreWithinError(joml.m13().toDouble(), cave[3, 1], 0.001)

        assertAreWithinError(joml.m20().toDouble(), cave[0, 2], 0.001)
        assertAreWithinError(joml.m21().toDouble(), cave[1, 2], 0.001)
        assertAreWithinError(joml.m22().toDouble(), cave[2, 2], 0.001)
        assertAreWithinError(joml.m23().toDouble(), cave[3, 2], 0.001)

        assertAreWithinError(joml.m30().toDouble(), cave[0, 3], 0.001)
        assertAreWithinError(joml.m31().toDouble(), cave[1, 3], 0.001)
        assertAreWithinError(joml.m32().toDouble(), cave[2, 3], 0.001)
        assertAreWithinError(joml.m33().toDouble(), cave[3, 3], 0.001)
    }

    @Test
    fun rowDotColumnShouldEqualProduct() {
        val joml = getJomlProduct()
        val cave = Matrix4.from(createIdentifyableMatrix4DoubleArray())

        for (row in 0..3) {
            for (column in 0..3) {
                val expected = joml[column, row].toDouble()
                val actual = cave.row[row].dot(cave.column[column])

                assertAreWithinError(expected, actual, 0.001, ", at row: $row, column: $column")
            }
        }
    }

    @Test
    fun testMatrix4Multiplication() {
        assertMatricesAreEqual(getJomlProduct(), getCaveProduct())
    }

    @Test
    fun testSetTranslation() {
        val cave = Matrix4.identity()
        val joml = Matrix4f().identity()

        cave.translation.set(1.2, 3.4, 5.6)
        joml.translation(1.2f, 3.4f, 5.6f)

        assertMatricesAreEqual(joml, cave)
    }

    @Test
    fun setTranslationThenApplyScale() {
        val cave = Matrix4.identity()
        val joml = Matrix4f().identity()

        cave.translation.set(1.2, 3.4, 5.6)
        joml.translation(1.2f, 3.4f, 5.6f)

        cave.scale.apply(7.8, 9.1, 2.3)
        joml.scale(7.8f, 9.1f, 2.3f)

        val scale3f = Vector3f()
        joml.getScale(scale3f)

        val translation3f = Vector3f()
        joml.getTranslation(translation3f)

        assertVectorsAreEqual(scale3f, cave.scale)
        assertVectorsAreEqual(translation3f, cave.translation)

        assertMatricesAreEqual(joml, cave)
    }

    @Test
    fun setScaleThenApplyTranslate() {
        val cave = Matrix4.identity()
        val joml = Matrix4f().identity()

        cave.scale.set(1.2, 3.4, 5.6)
        joml.scale(1.2f, 3.4f, 5.6f)

        cave.translation.apply(7.8, 9.1, 2.3)
        joml.translate(7.8f, 9.1f, 2.3f)

        val scale3f = Vector3f()
        joml.getScale(scale3f)

        val translation3f = Vector3f()
        joml.getTranslation(translation3f)

        assertVectorsAreEqual(scale3f, cave.scale)
        assertVectorsAreEqual(translation3f, cave.translation)

        assertMatricesAreEqual(joml, cave)
    }

    @Test
    fun testPerspectiveMatrix() {
        val fovy = Math.toRadians(70.0)
        val aspectRatio = 16.0/9.0

        val joml = Matrix4f().perspective(fovy.toFloat(), aspectRatio.toFloat(), 0f, Float.POSITIVE_INFINITY)
        val cave = Matrix4.perspective(fovy.radians, aspectRatio, 0.0, Double.POSITIVE_INFINITY)

        assertMatricesAreEqual(joml, cave)
    }

    @Test
    fun testRotation() {
        val angle = Math.toRadians(30.0)
        val cave = Matrix4.identity()
        val joml = Matrix4f().identity()

        joml.rotate(angle.toFloat(), Vector3f(0f, 0f, 1f))
        cave.rotation.apply(angle.radians, 0.0, 0.0, 1.0)

        assertMatricesAreEqual(joml, cave)
    }

    @Test
    fun testRotationThenApplyTranslation() {
        val angle = Math.toRadians(30.0)

        val cave = Matrix4.identity()
        val joml = Matrix4f().identity()

        cave.rotation.set(angle.radians, 0.0, 0.0, 1.0)
        cave.translation.apply(1.2, 3.4)

        joml.rotation(angle.toFloat(), 0f, 0f, 1f)
        joml.translate(1.2f, 3.4f, 0f)

        assertMatricesAreEqual(joml, cave)
    }

    @Test
    fun testTranslationThenApplyRotation() {
        val angle = Math.toRadians(30.0)

        val cave = Matrix4.identity()
        val joml = Matrix4f().identity()

        cave.translation.set(1.2, 3.4)
        cave.rotation.apply(angle.radians)

        joml.translate(1.2f, 3.4f, 0f)
        joml.rotate(angle.toFloat(), 0f, 0f, 1f)

        assertMatricesAreEqual(joml, cave)
    }

    private fun getJomlProduct(): Matrix4f {
        val jomlM1 = createMatrix4f(createIdentifyableMatrix4DoubleArray())
        val jomlM2 = createMatrix4f(createIdentifyableMatrix4DoubleArray())

        jomlM1.mul(jomlM2)

        return jomlM1
    }

    private fun getCaveProduct(): Matrix4 {
        val caveM1 = Matrix4.from(createIdentifyableMatrix4DoubleArray())
        val caveM2 = Matrix4.from(createIdentifyableMatrix4DoubleArray())

        caveM1 *= caveM2

        return caveM1

    }


    private fun multiply(m1: Matrix4, m2: Matrix4, row: Int, column: Int) {
        val result = m1.row[row].dot(m2.column[column])
        if (row < 3) {
            multiply(m1, m2, row + 1, column)
        } else if (column < 3) {
            multiply(m1, m2, 0, column+1)
        }
        m1[row, column] = result
    }

    private fun Matrix4.toDoubleArray(): DoubleArray {
        val array = DoubleArray(16)
        this.fill(array)
        return array
    }

    private fun assertMatricesAreEqual(joml: Matrix4f, cave: Matrix4) {
        val actual = joml.toDoubleArray()
        val expected = cave.toDoubleArray()

        if (!actual.contentEquals(expected, 0.01)) {
            val failIndex = actual.findUnequal(expected, 0.01)
            fail("""
                expected (JOML): ${expected.contentToString()}
                actual (CAVE):   ${actual.contentToString()}
                Failed at: actual[$failIndex] = ${actual[failIndex]}, expected[$failIndex] = ${expected[failIndex]}
                
            """.trimIndent())
        }
    }

    private fun assertVectorsAreEqual(vector3f: Vector3f, vector3: Vector3) {
        val xf = vector3f.x
        val yf = vector3f.y
        val zf = vector3f.z

        val (x, y, z) = vector3

        fun assertComponentsAreEqual(actual: Float, expected: Double, name: String) {
            assertAreWithinError(actual.toDouble(), expected, 0.01, "Value $name of vector: $vector3 is not equal to $name in $vector3f")
        }

        assertComponentsAreEqual(xf, x, "x")
        assertComponentsAreEqual(yf, y, "y")
        assertComponentsAreEqual(zf, z, "z")
    }

    private fun assertAreWithinError(expected: Double, actual: Double, error: Double, appendMessage: String = "") {
        assert(areWithinError(expected, actual, error)) { "expected: <$expected> but got: <$actual>$appendMessage" }
    }
}