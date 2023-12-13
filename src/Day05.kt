import kotlin.math.max
import kotlin.math.min

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
                        .let { (dest, source, length) -> (dest..dest + length) to (source..source + length) }
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
    val seedRanges = almanac.seeds.chunked(2).map { (start, length) -> start..<(start + length) }

    return seedRanges
        .flatMap { seedRange ->
            almanac.maps
                // apply mapping on matching subranges
                .fold(listOf(seedRange)) { acc, map ->
                    acc
                        .flatMap { range ->
                            val mapped = map.mapNotNull { (dest, source) ->
                                val subrange = max(source.first, range.first)..min(source.last, range.last)

                                if (subrange.isEmpty()) null
                                else {
                                    val shift = dest.first - source.first
                                    subrange to (subrange.first + shift)..(subrange.last + shift)
                                }
                            }

                            val fixedPoints = mapped
                                .map { it.first }
                                .sortedBy { it.first }
                                .flatMap { listOf(it.first, it.last) }
                                .let { listOf(range.first) + it + listOf(range.last) }
                                .chunked(2)
                                .mapNotNull { (start, end) ->
                                    if (start >= end) null
                                    else if (end == range.last) start.. end else start..<end
                                }

                            mapped.map { it.second } + fixedPoints
                        }
                }
        }
        .minOf { it.first }
}

fun main() {
    val testInput = parse(rawTestInput)
    val input = parse(readDayInput(5).joinToString("\n"))

    // PART 1
    assertEquals(part1(testInput), 35)
    println("Part1: ${part1(input)}")

    // PART 2
    assertEquals(part2(testInput), 46)
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