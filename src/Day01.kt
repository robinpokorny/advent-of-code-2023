private fun parse(input: List<String>) = input

private val substitutions = listOf(
    "one" to "1",
    "two" to "2",
    "three" to "3",
    "four" to "4",
    "five" to "5",
    "six" to "6",
    "seven" to "7",
    "eight" to "8",
    "nine" to "9",
)

private fun part1(input: List<String>): Int = input
    .sumOf { line -> line
        .mapNotNull { it.toString().toIntOrNull() }
        .let { it.first() * 10 + it.last() }
    }

private fun findFirstDigit(line: String): Int = line
    .findAnyOf(substitutions.flatMap { it.toList() }, 0, true)
    ?.second!!
    .let { substitutions.fold(it) { acc, (old, new) -> acc.replace(old, new, true) } }
    .toInt()

private fun findLastDigit(line: String): Int = line
    .findLastAnyOf(substitutions.flatMap { it.toList() }, ignoreCase = true)
    ?.second!!
    .let { substitutions.fold(it) { acc, (old, new) -> acc.replace(old, new, true) } }
    .toInt()

private fun part2(input: List<String>): Int = input
    .sumOf { findFirstDigit(it) * 10 + findLastDigit(it) }

fun main() {
    println(findFirstDigit("xtwone3four"))

    val testInput = parse(rawTestInput)
    val testInput2 = parse(rawTestInput2)
    val input = parse(readDayInput(1))

    // PART 1
    assertEquals(part1(testInput), 142)
    println("Part1: ${part1(input)}")

    // PART 2
    assertEquals(part2(testInput2), 281)
    println("Part2: ${part2(input)}")
}

private val rawTestInput = """
    1abc2
    pqr3stu8vwx
    a1b2c3d4e5f
    treb7uchet
""".trimIndent().lines()

private val rawTestInput2 = """
    two1nine
    eightwothree
    abcone2threexyz
    xtwone3four
    4nineeightseven2
    zoneight234
    7pqrstsixteen
""".trimIndent().lines()