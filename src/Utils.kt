import kotlin.io.path.Path
import kotlin.io.path.readLines

/**
 * Reads lines from the given input txt file.
 */
fun readDayInput(day: Int): List<String> {
    val fileName = day.toString().padStart(2, '0')
    return Path("src/Day$fileName.txt").readLines()
}

/**
 * Compares the two inputs and throws if they are not equal
 */
fun <T> assertEquals(actual: T, expected: T) {
    check(actual == expected) { "Assert failed: expected `$expected`, received `$actual`" }
}

/**
 * 2D point
 */
data class Point(val x: Int, val y: Int) {
    operator fun plus(other: Point) = Point(other.x + x, other.y + y)

    companion object {
        val LEFT = Point(-1, 0)
        val RIGHT = Point(1, 0)
        val UP = Point(0, 1)
        val DOWN = Point(0, -1)
        val AROUND = listOf(
            UP + LEFT, UP, UP + RIGHT,
            LEFT, RIGHT,
            DOWN + LEFT, DOWN, DOWN + RIGHT
        )
    }
}
