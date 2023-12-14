private fun parse(input: List<String>) = input

val countWinningWays: (Pair<Long, Long>) -> Int = { (time, record) ->
    (1..<time)
        .map { (time - it) * it }
        .count { it > record }
}

private fun part1(input: List<String>): Int = input
    .map { it.split(Regex("\\s+")).drop(1).map(String::toLong) }
    .let { it[0].zip(it[1]) }
    .map(countWinningWays)
    .reduce { acc, i -> acc * i }

private fun part2(input: List<String>): Int = input
    .map { it.substringAfter(':').replace(" ", "").toLong() }
    .zipWithNext()
    .single()
    .let(countWinningWays)

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