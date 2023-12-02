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

    fun part1Pretentious(input: List<String>): Int {
        return input
            .map { Pair(it.find { it.isDigit() }!!, it.findLast { it.isDigit() }!!) }
            .sumOf { "${it.first}${it.second}".toInt() }
    }

    fun part1Golf(input: List<String>): Int = input.sumOf{"${it.find{it.isDigit()}!!}${it.findLast{it.isDigit()}!!}".toInt()}

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
        return sum.also { println(it) }
    }

    fun part2Pretentious(input: List<String>): Int = input
        .map { regex.findAllOverlapping(it) }
        .sumOf { "${it.first().toNumber()}${it.last().toNumber()}".toInt() }.also { println(it) }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == part1Golf(testInput))

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
    check(part2(input) == part2Pretentious(input))

}
