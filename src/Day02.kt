fun main() {

    // I *could* use a data class here, but if I do that, it makes it harder to
    // iterate across colors.
    fun parseGame(input: String): Pair<Int, List<Map<String, Int>>> {
        val (numberSection, roundsSection) = input.split(":")
        val number = numberSection.split(" ")[1].toInt()

        val game = mutableListOf<Map<String, Int>>()
        for (round in roundsSection.split(";")) {
            val roundMap = mutableMapOf<String, Int>()
            for (rawDraw in round.split(",")) {
                val draw = rawDraw.trim()
                val (count, color) = draw.split(" ")
                roundMap[color] = count.toInt()
            }
            game.add(roundMap)
        }
        return Pair(number, game)
    }

    fun part1(input: List<String>): Int {
        var sum = 0
        for (line in input) {
            val (number, game) = parseGame(line)
            var legalGame = true
            for (round in game) {
                if (((round["red"] ?: 0) > 12)
                    or ((round["green"] ?: 0) > 13)
                    or ((round["blue"] ?: 0) > 14)) {
                    legalGame = false
                    break
                }
            }
            if (legalGame) {
                sum += number
            }
        }
        return sum
    }

    fun part2(input: List<String>): Int {
        var totalPower = 0
        for (line in input) {

            val (_, game) = parseGame(line)
            val minimumCount = mutableMapOf("red" to 0, "blue" to 0, "green" to 0)
            for (round in game) {
                for (color in round.keys) {
                    val number = round[color]!!
                    val minNumber = minimumCount[color]!!
                    if (number > minNumber) {
                        minimumCount[color] = number
                    }
                }
            }
            val power = minimumCount.values.fold(1) { acc, num -> acc * num }
            totalPower += power
        }
        return totalPower
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test_1")
    check(part1(testInput) == 8)
    check(part2(testInput) == 2286)

    val input = readInput("Day02")
    check(part1(input) == 2283)
    check(part2(input) == 78669)
}
