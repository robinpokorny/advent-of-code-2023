typealias Schematic = Map<Point, List<Int>>

private fun parse(input: List<String>) = input


private fun toSchematic(input: List<String>): Schematic = input
    .flatMapIndexed { i, line ->
        Regex("\\d+")
            .findAll(line)
            .flatMap { match ->
                match
                    .range
                    .flatMap { position ->
                        val current = Point(i, position)
                        Point.AROUND.map { current + it }
                    }
                    .toSet()
                    .filter {
                        input
                            .getOrNull(it.x)
                            ?.getOrNull(it.y)
                            ?.let { char -> !char.isDigit() && char != '.' }
                            ?: false
                    }
                    .map { it to match.value.toInt() }
            }
    }
    .groupBy({ it.first }, { it.second })

private fun part1(input: List<String>): Int = toSchematic(input)
    .flatMap { it.value }.sum()

private fun part2(input: List<String>): Int = toSchematic(input)
    .filter { (point, values) -> input[point.x][point.y] == '*' && values.size == 2 }
    .values
    .sumOf { (a, b) -> a * b }

fun main() {
    val testInput = parse(rawTestInput)
    val input = parse(readDayInput(3))

    // PART 1
    assertEquals(part1(testInput), 4361)
    println("Part1: ${part1(input)}")

    // PART 2
    assertEquals(part2(testInput), 467835)
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