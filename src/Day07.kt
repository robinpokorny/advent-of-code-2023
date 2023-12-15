val cardStrength = "23456789TJQKA".toList().reversed()

val Joker = cardStrength.indexOf('J')
val weakJoker = 100 // higher than any other card

enum class Kind() {
    FIVE_OF_A_KIND,
    FOUR_OF_A_KIND,
    FULL_HOUSE,
    THREE_OF_A_KIND,
    TWO_PAIRS,
    ONE_PAIR,
    HIGH_CARD
}

private data class Hand(
    val cards: List<Int>,
    val bid: Int,
    val kind: Kind = Kind.HIGH_CARD
)

private fun parse(input: List<String>) = input
    .map { line ->
        val (cards, bid) = line.split(" ")

        Hand(cards.map { cardStrength.indexOf(it) }, bid.toInt())
    }

private fun getKind(labels: Collection<Int>) = when {
    labels.contains(5) -> Kind.FIVE_OF_A_KIND
    labels.contains(4) -> Kind.FOUR_OF_A_KIND
    labels.contains(3) && labels.contains(2) -> Kind.FULL_HOUSE
    labels.contains(3) -> Kind.THREE_OF_A_KIND
    labels.count { it == 2 } == 2 -> Kind.TWO_PAIRS
    labels.contains(2) -> Kind.ONE_PAIR
    else -> Kind.HIGH_CARD
}

private val handComparator = compareBy<Hand>(
    { it.kind },
    { it.cards[0] },
    { it.cards[1] },
    { it.cards[2] },
    { it.cards[3] },
    { it.cards[4] })

private fun part1(input: List<Hand>): Int = input
    .map { hand ->
        val labels = hand.cards.groupingBy { it }.eachCount().values

        val kind = getKind(labels)

        hand.copy(kind = kind)
    }
    .sortedWith(handComparator)
    .reversed()
    .mapIndexed { index, hand -> (index + 1) * hand.bid }
    .sum()

private fun part2(input: List<Hand>): Int = input
    .map { hand ->
        hand
            // Make Jokers the weakest card
            .copy(cards = hand.cards.map { if (it == Joker) weakJoker else it })
    }
    .map { hand ->
        val groups = hand.cards.groupingBy { it }.eachCount()

        val jokers = groups[weakJoker] ?: 0

        val labelsWithoutJokers = (groups - (weakJoker)).values.sortedDescending()

        // Add count the jokers as the most common card
        val updatedCards = listOf((labelsWithoutJokers.firstOrNull() ?: 0) + jokers) + labelsWithoutJokers.drop(1)

        val kind = getKind(updatedCards)

        hand.copy(kind = kind)
    }
    .sortedWith(handComparator)
    .reversed()
    .mapIndexed { index, hand -> (index + 1) * hand.bid }
    .sum()

fun main() {
    val testInput = parse(rawTestInput)
    val input = parse(readDayInput(7))

    // PART 1
    assertEquals(part1(testInput), 6440)
    println("Part1: ${part1(input)}")

    // PART 2
    assertEquals(part2(testInput), 5905)
    println("Part2: ${part2(input)}")
}

private val rawTestInput = """
    32T3K 765
    T55J5 684
    KK677 28
    KTJJT 220
    QQQJA 483
""".trimIndent().lines()