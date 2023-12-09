data class Race(val totalTime:Long, val recordDistance:Long) {
    fun waysToWin():Int {
        var ways = 0
        for (buttonTime in 0..totalTime) {
            val distance = buttonTime * (totalTime - buttonTime)
            if (distance > recordDistance) {
                ways++
            }
        }
        return ways
    }
}

fun parseRaces(input:List<String>): List<Race> {

    fun parseLine(line: String): List<Long> =
        line.split(":").last().split(' ').filter { it.isNotBlank() }.map { it.toLong() }


    val times = parseLine(input.first())
    val distances = parseLine(input.last())

    return times.zip(distances).map { Race(it.first, it.second) }
}

fun main() {
    fun part1(input: List<String>): Int {
        return parseRaces(input).map { it.waysToWin() }.fold(1) {acc, n -> acc * n}
    }

    fun part2(input: List<String>): Int {

        fun String.parseLine(): Long = this.split(':').last().filter { it != ' ' }.toLong()

        val time = input.first().parseLine()
        val distance = input.last().parseLine()

        return Race(time, distance).waysToWin()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 288)
    check(part2(testInput).alsoPrintln() == 71503)
    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}
