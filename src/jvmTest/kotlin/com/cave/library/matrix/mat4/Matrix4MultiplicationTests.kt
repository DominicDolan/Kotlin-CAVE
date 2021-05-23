package com.cave.library.matrix.mat4

import org.junit.Test

class Matrix4MultiplicationTests {
    @Test
    fun testBasicMultiplication() {
        val actual = Matrix4.identity()
        val multiplier = StaticMatrix4.identity()

        actual *= multiplier

        val expected = StaticMatrix4.from(doubleArrayOf(
            1.0, 0.0, 0.0, 0.0,
            0.0, 1.0, 0.0, 0.0,
            0.0, 0.0, 1.0, 0.0,
            0.0, 0.0, 0.0, 1.0,
        ))

        val comparer = Matrix4Comparer(actual, expected)

        assert(comparer.areEqual()) {
            "Matrices are not equal after multiplying an identity matrix:$comparer"
        }
    }

    @Test
    fun testTranslationMultiplication() {
        val x = 2.0; val y = 3.0; val z = 0.0
        val multiplier = StaticMatrix4.translation(x, y, z)

        val actual = Matrix4.identity()

        actual *= multiplier

        val expected = Matrix4.from(doubleArrayOf(
            1.0, 0.0, 0.0, x,
            0.0, 1.0, 0.0, y,
            0.0, 0.0, 1.0, z,
            0.0, 0.0, 0.0, 1.0,
        ))

        println(expected)

        val comparer = Matrix4Comparer(actual, expected)

        assert(comparer.areEqual()) {
            "Matrices are not equal after multiplying a translation matrix:$comparer"
        }

    }

    @Test
    fun testScaleMultiplication() {
        val x = 2.0; val y = 3.0; val z = 1.0
        val actual = Matrix4.identity()
        val multiplier = StaticMatrix4.scaled(x, y, z)

        actual *= multiplier

        val expected = StaticMatrix4.from(doubleArrayOf(
             x,  0.0, 0.0, 0.0,
            0.0,  y,  0.0, 0.0,
            0.0, 0.0,  z,  0.0,
            0.0, 0.0, 0.0, 1.0,
        ))

        val comparer = Matrix4Comparer(actual, expected)

        assert(comparer.areEqual()) {
            "Matrices are not equal after multiplying a scaled matrix:$comparer"
        }

    }

    @Test
    fun testRotatedMultiplication() {
//        val m1 = Matrix4.identity()
//        val m2 = Matrix4.identity().applyRotation(30.degrees, 0.0, 0.0, 1.0)
//
//        m1 *= m2
//
//        assertMatrixIsIdentity(m1)
//        assert(matricesAreEqual(m1, m2))

    }

    @Test
    fun testScaleTranslationAndRotationMultiplication() {
//        val m1 = Matrix4.identity()
//        val m2 = Matrix4.identity()
//            .applyTranslation(1.0, 2.0, 0.0)
//            .applyRotation(30.degrees, 0.0, 0.0, 1.0)
//            .applyScale(1.0, 2.0, 0.0)
//
//        m1 *= m2
//
//        assertMatrixIsIdentity(m1)
//        assert(matricesAreEqual(m1, m2))
    }


}