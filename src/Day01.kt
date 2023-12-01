import java.lang.Exception

fun main() {
    fun part1(input: List<String>): Int {
        var sum = 0
        for (line in input) {
            val first = line.find { it.isDigit() }!!
            val last = line.findLast { it.isDigit() }!!
            val lineValue = "$first$last".toInt()
            sum += lineValue
        }
        return sum
    }

    fun String.toNumber(): Int {
        return try {
            this.toInt()
        } catch (e: NumberFormatException) {
            when (this) {
                "one" -> 1
                "two" -> 2
                "three" -> 3
                "four" -> 4
                "five" -> 5
                "six" -> 6
                "seven" -> 7
                "eight" -> 8
                "nine" -> 9
                else -> throw Exception("Weird number $this")
            }
        }
    }

    // The actual Regex.findAll doesn't allow matches to overlap
    fun Regex.findAllOverlapping(target: String): List<String> {
        val matches = mutableListOf<String>()
        for (i in 0..target.length) {
            val matchResult = this.matchAt(target, i)
            if (matchResult != null) {
                matches.add(matchResult.value)
            }
        }
        return matches
    }

    val regex = Regex("\\d|one|two|three|four|five|six|seven|eight|nine")
    fun part2(input: List<String>): Int {
        var sum = 0
        for (line in input) {
            val matches = regex.findAllOverlapping(line)
            val first = matches.first().toNumber()
            val last = matches.last().toNumber()
            val lineNumber = "$first$last".toInt()
            sum += lineNumber
        }
        return sum
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part2(testInput) == 281)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()

}
