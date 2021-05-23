package com.cave.library.matrix.arrayQuery

import com.cave.library.matrix.MatrixContext
import org.junit.Test
import kotlin.test.assertEquals

class ArrayQueryTests {
    @Test
    fun arrayQueryFor2x2() {
        val array = doubleArrayOf(
            0.0, 0.1,
            1.0, 1.1,
        )

        val expected = 1.0
        val actual = BasicArrayQuery(array, 2, 2).get(1, 0)

        assertEquals(expected, actual, "array of\n${array.contentToString()}\n does not return a value of $expected at position column: 3, row: 2)\n")

    }


    @Test
    fun arrayQueryGets32CorrectlyFor4x4() {
        val expectedAt32 = 3.2
        val array = doubleArrayOf(
            0.0, 0.1, 0.2, 0.3,
            1.0, 1.1, 1.2, 1.3,
            2.0, 2.1, 2.2, 2.3,
            3.0, 3.1, 3.2, 3.3,
        )

        val actual = BasicArrayQuery(array, 4, 4).get(3, 2)

        assertEquals(expectedAt32, actual, "array of\n${array.contentToString()}\n does not return a value of $expectedAt32 at position column: 3, row: 2)\n")

    }

    class BasicArrayQuery(private val array: DoubleArray, override val columnCount: Int, override val rowCount: Int) : MatrixContext {
        fun get(row: Int, column: Int): Double {
            return array[row, column]
        }
    }
}