data class Almanac(val seeds: List<Long>, val maps: List<List<Pair<LongRange, LongRange>>>)

private fun parse(input: String): Almanac {
    val sections = input.split("\n\n")

    val seeds = sections[0]
        .split(": ")[1]
        .split(" ")
        .map { it.toLong() }

    val maps = sections
        .drop(1)
        .map { section ->
            section
                .lines()
                .drop(1)
                .map { line ->
                    line.split(" ")
                        .map { it.toLong() }
                        .let { (dest, source, length) -> (dest..dest+length) to (source..source+length) }
                }
        }

    return Almanac(seeds, maps)
}
private fun part1(almanac: Almanac): Long = almanac.seeds
    .map { seed ->
        almanac.maps
            .fold(seed) { acc, map ->
                map
                    .firstOrNull { (_, source) -> source.contains(acc) }
                    ?.let { (dest, source) -> acc + dest.first - source.first }
                    ?: acc
            }
    }
    .min()

private fun part2(almanac: Almanac): Long {
    return 0
}

fun main() {
    val testInput = parse(rawTestInput)
    val input = parse(readDayInput(5).joinToString("\n"))

    // PART 1
    assertEquals(part1(testInput), 35)
    println("Part1: ${part1(input)}")

    // PART 2
    assertEquals(part2(testInput), 0)
    println("Part2: ${part2(input)}")
}

private val rawTestInput = """
    seeds: 79 14 55 13

    seed-to-soil map:
    50 98 2
    52 50 48

    soil-to-fertilizer map:
    0 15 37
    37 52 2
    39 0 15

    fertilizer-to-water map:
    49 53 8
    0 11 42
    42 0 7
    57 7 4

    water-to-light map:
    88 18 7
    18 25 70

    light-to-temperature map:
    45 77 23
    81 45 19
    68 64 13

    temperature-to-humidity map:
    0 69 1
    1 0 69

    humidity-to-location map:
    60 56 37
    56 93 4
""".trimIndent()