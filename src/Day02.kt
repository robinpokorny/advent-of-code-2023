typealias Game = Pair<Int, List<Map<String, Int>>>

private fun parse(input: List<String>): List<Game> = input
    .map {
        val (idRaw, gameRaw) = it.split(": ")
        val (_, id) = idRaw.split(" ")
        val counts = gameRaw.split("; ")
            .map {
                it
                    .split(", ")
                    .map { it.split(" ") }
                    .map { (count, color) -> color to count.toInt() }
                    .toMap()
            }

        (id.toInt() to counts)
    }

private fun part1(input: List<Game>): Int = input
    .filter { (_, counts) ->
        counts.all {
            it.getOrDefault("red", 0) <= 12 &&
                it.getOrDefault("green", 0) <= 13 &&
                it.getOrDefault("blue", 0) <= 14
        }
    }
    .sumOf { (id, _) -> id }

private fun part2(input: List<Game>): Int {
    return 0
}

fun main() {
    val testInput = parse(rawTestInput)
    println(testInput)

    val input = parse(readDayInput(2))

    // PART 1
    assertEquals(part1(testInput), 8)
    println("Part1: ${part1(input)}")

    // PART 2
    assertEquals(part2(testInput), 0)
    println("Part2: ${part2(input)}")
}

private val rawTestInput = """
    Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
    Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
    Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
    Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
    Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green
""".trimIndent().lines()