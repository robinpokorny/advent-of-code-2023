private fun parse(input: List<String>) = input

private fun part1(input: List<String>): Int = input
    .map { it.split(Regex("\\s+")).drop(1).map(String::toInt) }
    .let { it[0].zip(it[1]) }
    .map { (time, record) ->
        (1..<time)
            .map { (time - it) * it }
            .count { it > record }
    }
    .reduce { acc, i -> acc * i }

private fun part2(input:  List<String>): Int {
    return 0
}

fun main() {
    val testInput = parse(rawTestInput)
    val input = parse(readDayInput(6))

    // PART 1
    assertEquals(part1(testInput), 288)
    println("Part1: ${part1(input)}")

    // PART 2
    assertEquals(part2(testInput), 71503)
    println("Part2: ${part2(input)}")
}

private val rawTestInput = """
    Time:      7  15   30
    Distance:  9  40  200
""".trimIndent().lines()