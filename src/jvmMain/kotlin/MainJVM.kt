import com.cave.library.angle.degrees
import com.cave.library.angle.plus
import com.cave.library.matrix.mat3.Matrix3
import com.cave.library.matrix.mat3.StaticMatrix3

fun main() {

    var inputAngle = 0.degrees
    val m = Matrix3.identity()

    while (inputAngle.toDouble() < 180.0) {
        m.rotation.angle = inputAngle.toRadians()

        println("input angle:    $inputAngle")
        println("rotation angle: ${m.rotation.toDegrees()}")
        println()
        inputAngle += 5.degrees
        Thread.sleep(500)
    }

}

fun testStaticRotation() {

    var inputAngle = 0.degrees

    while (inputAngle.toDouble() < 180.0) {
        val sr = StaticMatrix3.rotated(inputAngle, 0.0, 0.0, 1.0)

        println("input angle:    $inputAngle")
        println("rotation angle: ${sr.rotation.toDegrees()}")
        println()
        inputAngle += 5.degrees
        Thread.sleep(500)
    }

}