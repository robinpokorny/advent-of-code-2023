import kotlin.math.max

data class Cubes(val green: Int = 0, val red: Int = 0, val blue: Int = 0)

typealias Game = Pair<Int, List<Cubes>>

private fun parse(input: List<String>): List<Game> = input
    .map { line ->
        val (idRaw, gameRaw) = line.split(": ")
        val (_, id) = idRaw.split(" ")
        val counts = gameRaw.split("; ")
            .map { hand ->
                hand
                    .split(", ")
                    .map { it.split(" ") }
                    .map { (count, color) -> color to count.toInt() }
                    .toMap()
            }
            .map {
                Cubes(
                    green = it.getOrDefault("green", 0),
                    red = it.getOrDefault("red", 0),
                    blue = it.getOrDefault("blue", 0),
                )
            }

        id.toInt() to counts
    }

private fun part1(input: List<Game>): Int = input
    .filter { (_, counts) ->
        counts.all {
            it.red <= 12 && it.green <= 13 && it.blue <= 14
        }
    }
    .sumOf { (id, _) -> id }

private fun part2(input: List<Game>): Int = input
    .map { it.second }
    .map { counts ->
        counts.fold(Cubes()) { acc, cubes ->
            Cubes(
                green = max(acc.green, cubes.green),
                red = max(acc.red, cubes.red),
                blue = max(acc.blue, cubes.blue)
            )
        }
    }
    .sumOf { (green, red, blue) -> green * red * blue }

fun main() {
    val testInput = parse(rawTestInput)
    println(testInput)

    val input = parse(readDayInput(2))

    // PART 1
    assertEquals(part1(testInput), 8)
    println("Part1: ${part1(input)}")

    // PART 2
    assertEquals(part2(testInput), 2286)
    println("Part2: ${part2(input)}")
}

private val rawTestInput = """
    Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
    Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
    Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
    Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
    Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green
""".trimIndent().lines()