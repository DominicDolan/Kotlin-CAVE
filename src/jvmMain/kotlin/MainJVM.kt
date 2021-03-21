import com.cave.library.angle.degrees
import com.cave.library.angle.plus
import com.cave.library.matrix.mat3.Matrix3
import com.cave.library.matrix.mat3.StaticMatrix3
import com.cave.library.matrix.mat4.Matrix4
import org.joml.Matrix4f
import org.joml.Vector3f

fun main() {

    val m = Matrix4f().translate(1f, 2f, 3f)

    val m2 = Matrix4.translation(1.0, 2.0, 3.0)

    println(m)
    println(m2)
    println()

    val translation = Vector3f()
    m.getTranslation(translation)
    println(translation)

    println(m2.translation.z)
    val array = FloatArray(16)

    m.get(array)

    println(array.contentToString())

    val array2 = FloatArray(16)
    m2.fill(array2)
    println(array2.contentToString())

}

fun testVariableRotation() {

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