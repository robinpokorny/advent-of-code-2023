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
