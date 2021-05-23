package com.cave.library.matrix.mat4

import com.cave.library.testUtils.areWithinError

class Matrix4Comparer(val m1: StaticMatrix4, val m2: StaticMatrix4, val allowableError: Double = 0.001) {
    inline fun compare(cellAction: Matrix4Comparer.(row: Int, column: Int) -> Unit) {
        for (row in 0..3) {
            for (column in 0..3) {
                cellAction(this, column, row)
            }
        }
    }

    inline fun areEqual(failAction: Matrix4Comparer.(row: Int, column: Int) -> Unit = {_, _ ->}): Boolean {
        compare { column, row ->
            val areEqual = areWithinError(m1[row, column], m2[row, column], allowableError)
            if (!areEqual) {
                failAction(column, row)
                return false
            }
        }

        return true
    }

    override fun toString(): String {
        return "\n" +
                "m1:\n$m1\n" +
                "m2:\n$m2"
    }
}