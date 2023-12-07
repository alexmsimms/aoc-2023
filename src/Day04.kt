import kotlin.math.pow


data class Card(val index: Int, val scoringNumbers: Set<Int>, val numbers: List<Int> ) {
    companion object {
        fun fromString(input: String): Card {
            val (indexSide, gameSide) = input.split(':')
            val index = indexSide.split(' ').last().toInt()
            val (scoringSide, numberSide) = gameSide.split('|').map {it.trim()}

            val scoringNumbers = scoringSide.split(' ')
                .filter { it.isNotBlank() } // Some of the numbers have leading spaces
                .map { it.toInt() }
                .toCollection(mutableSetOf())

            val numbers = numberSide.split(' ')
                .filter { it.isNotBlank() } // Some of the numbers have leading spaces
                .map { it.toInt() }
                .toCollection(mutableListOf())

            return Card(index, scoringNumbers, numbers)
        }
    }

    val matches: Int = numbers.filter { this.scoringNumbers.contains(it) }.size
    val score: Int = 2.0.pow(matches-1).toInt()

}


fun main() {
    fun part1(input: List<String>): Int {
        return input.map { Card.fromString(it) }.sumOf { it.score }
    }

    fun part2(input: List<String>): Int {
        val parsedInput = input.map { Card.fromString(it) }
        val cards = mutableListOf(Card(0, setOf(), listOf())) // Zeroth card to make the indexes work
        cards.addAll(parsedInput)

        var totalCards = 0

        val deck = ArrayDeque<Card>()
        deck.addAll(parsedInput)

        while (deck.isNotEmpty()) {
            val card = deck.removeFirst()
            totalCards++
            for (i in (card.index+1) .. (card.index + card.matches)) {
                deck.add(cards[i])
            }
        }
        return totalCards
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 13)
    check(part2(testInput).also { println(it) } == 30)

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
