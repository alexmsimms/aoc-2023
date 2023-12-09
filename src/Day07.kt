class Hand(input: String, joker: Boolean = false): Comparable<Hand> {
    private val splitInput = input.trim().split(' ')
    private val text = splitInput.first()
    val bid = splitInput.last().toInt()
    private val kind = Kind.classify(text, joker)

    override fun toString(): String = "Hand($text, $kind, $bid)"

    private val cardValues = mapOf(
        'A' to 100,
        'K' to 90,
        'Q' to 80,
        'J' to if (joker) 0 else 70,
        'T' to 60,
        '9' to 9,
        '8' to 8,
        '7' to 7,
        '6' to 6,
        '5' to 5,
        '4' to 4,
        '3' to 3,
        '2' to 2
        )


    override fun compareTo(other: Hand): Int {
        val kindSort = this.kind compareTo other.kind
        if (kindSort != 0) {
            return kindSort
        }
        val winningPair = this.text.zip(other.text).find { cardValues[it.first] != cardValues[it.second] }
        winningPair ?: return 0

        return cardValues[winningPair.first]!! - cardValues[winningPair.second]!!

    }
}
enum class Kind {
    HIGH_CARD, ONE_PAIR, TWO_PAIR, THREE_OF_A_KIND, FULL_HOUSE, FOUR_OF_A_KIND, FIVE_OF_A_KIND;
    companion object {
        fun classify(input: String, joker: Boolean): Kind {
            val letterToCount = input.groupingBy { it }.eachCount().toMutableMap()

            if (joker) {
                val jokerCount = letterToCount['J'] ?: 0

                if (jokerCount in 1..4) {
                    val mostFrequentRealCard = letterToCount.filterNot { it.key == 'J' }.maxBy { it.value }
                    letterToCount[mostFrequentRealCard.key] = mostFrequentRealCard.value + jokerCount
                    letterToCount.remove('J')
                }
            }

            val countToCount = letterToCount.values.groupingBy { it }.eachCount()

            return when {
                countToCount.contains(5) -> FIVE_OF_A_KIND
                countToCount.contains(4) -> FOUR_OF_A_KIND
                countToCount.contains(3) -> if (countToCount.contains(2)) FULL_HOUSE else THREE_OF_A_KIND
                countToCount.contains(2) -> if (countToCount[2] == 2) TWO_PAIR else ONE_PAIR
                else -> HIGH_CARD
            }
        }
    }
}

fun main() {
    fun part1(input: List<String>): Int {
        return input.map { Hand(it) }
            .sorted()
            .foldIndexed(0) {index, acc, hand -> acc + (hand.bid * (index + 1))}
    }

    fun part2(input: List<String>): Int {
        return input.map { Hand(it, true) }
            .sorted()
            .foldIndexed(0) {index, acc, hand -> acc + (hand.bid * (index + 1))}
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 6440)
    check(part2(testInput) == 5905)

    val input = readInput("Day07")
    check(part1(input) == 249204891)
    check(part2(input) == 249666369)
}
