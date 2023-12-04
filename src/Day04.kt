import kotlin.math.pow

private data class Card(val id: Int, val matching: Int)

private fun parse(input: List<String>) = input
    .map {
        val (rawId, lists) = it.split(": ")

        val id = rawId.split(Regex("\\s+"))[1].toInt()

        val (winning, received) = lists
            .split(" | ")
            .map { list ->
                list
                    .trim()
                    .split(Regex("\\s+"))
                    .mapNotNull(String::toIntOrNull)
                    .toSet()
            }

        Card(id, winning.intersect(received).size)
    }

private fun part1(cards: List<Card>): Int = cards
    .sumOf { (_, matching) ->
        2.0.pow(matching - 1).toInt()
    }

private fun part2(cards: List<Card>): Int {
    val copies = cards.associate { it.id to 1 }.toMutableMap()

    cards.forEach { (id, matching) ->
        for (i in id + 1..id + matching)
            copies[i] = copies[i]!! + copies[id]!!
    }

    return copies.values.sum()
}

fun main() {
    val testInput = parse(rawTestInput)
    val input = parse(readDayInput(4))

    // PART 1
    assertEquals(part1(testInput), 13)
    println("Part1: ${part1(input)}")

    // PART 2
    assertEquals(part2(testInput), 30)
    println("Part2: ${part2(input)}")
}

private val rawTestInput = """
    Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53
    Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19
    Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1
    Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83
    Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36
    Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11
""".trimIndent().lines()