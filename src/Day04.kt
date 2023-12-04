import kotlin.math.pow

private fun parse(input: List<String>) = input
    .map {
        val (_, lists) = it.split(": ")

        val (winning, received) = lists
            .split(" | ")
            .map { list ->
                list
                    .trim()
                    .split(Regex("\\s+"))
                    .map { it.toInt() }
            }

        winning to received
    }

private fun part1(input: List<Pair<List<Int>, List<Int>>>): Int = input
    .sumOf { (winning, received) ->
        2.0.pow(winning.intersect(received).size -1).toInt()
    }
private fun part2(input:  List<Pair<List<Int>, List<Int>>>): Int {
    return 0
}

fun main() {
    val testInput = parse(rawTestInput)
    val input = parse(readDayInput(4))

    // PART 1
    assertEquals(part1(testInput), 13)
    println("Part1: ${part1(input)}")

    // PART 2
    assertEquals(part2(testInput), 0)
    // println("Part2: ${part2(input)}")
}

private val rawTestInput = """
    Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53
    Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19
    Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1
    Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83
    Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36
    Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11
""".trimIndent().lines()