import com.cave.library.angle.degrees
import com.cave.library.matrix.mat3.StaticMatrix3
import com.cave.library.vector.vec3.Vector3
import org.joml.AxisAngle4f
import org.joml.Matrix3f
import java.lang.Math.toDegrees
import java.lang.Math.toRadians

fun main() {
    println("Rotation")

    val joml = Matrix3f().rotate(toRadians(40.0).toFloat(), 0f, 0f, 1f)
    println(joml)
    val angle = AxisAngle4f()
    joml.getRotation(angle)
    println("AxisAngle4f: $angle, rotation: ${toDegrees(angle.angle.toDouble())}")
    val sr = StaticMatrix3.rotated(40.degrees, 0.0, 3.0, 4.0)
    println(sr)
    println("rotation angle: ${sr.rotation.rotation.toDegrees()}")
    println("rotation angle: ${sr.rotation.rotation.toRadians()}")

    println("axis: ${Vector3.from(sr.rotation)}")
}