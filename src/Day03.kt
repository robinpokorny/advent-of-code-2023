private fun parse(input: List<String>): List<String> = input

private fun part1(input: List<String>): Int {
    val symbols = input
        .map { it.toList() }
        .flatMapIndexed { i, line ->
            line.mapIndexedNotNull { j, char ->
                if (!char.isDigit() && char != '.') Point(i, j) else null
            }
        }
        .toSet()

    val nextToSymbol = input.flatMapIndexed { i, line ->
        Regex("\\d+")
            .findAll(line)
            .flatMap { match ->
                match
                    .range
                    .flatMap {
                        val current = Point(i, it)
                        Point.AROUND.map { current + it }
                    }
                    .intersect(symbols)
                    .map { it to match.value.toInt() }
            }
    }.groupBy({ it.first }, { it.second })

    return nextToSymbol.flatMap { it.value }.sum()
}

private fun part2(input: List<String>): Int {
    return 0
}

fun main() {
    val testInput = parse(rawTestInput)
    val input = parse(readDayInput(3))

    // PART 1
    assertEquals(part1(testInput), 4361)
    println("Part1: ${part1(input)}")

    // PART 2
    assertEquals(part2(testInput), 0)
    println("Part2: ${part2(input)}")
}

private val rawTestInput = """
    467..114..
    ...*......
    ..35..633.
    ......#...
    617*......
    .....+.58.
    ..592.....
    ......755.
    ...${'$'}.*....
    .664.598..
""".trimIndent().lines()