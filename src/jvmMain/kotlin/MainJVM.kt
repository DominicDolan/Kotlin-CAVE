import com.cave.library.matrix.mat4.StaticMatrix4
import com.cave.library.vector.vec2.plus
import com.cave.library.vector.vec2.vec

fun main() {
    val vec1 = vec(1.0, 2.0)
    val vec2 = vec(2.0, 1.0)

    println((vec1 + vec2))
    val m = StaticMatrix4.identity()
    println(m)
}