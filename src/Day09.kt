fun parseLine(line: String): List<Int> = line.split(' ').map { it.toInt() }

fun List<Int>.differenceList(): List<Int> = this.zipWithNext().map { it.second - it.first }

fun nextValue(input: List<Int>): Int {
    val stack = makeStack(input)

    var value = stack.removeFirst().last()
    while (stack.isNotEmpty()) {
        value += stack.removeFirst().last()
    }

    return value
}

fun previousValue(input: List<Int>): Int {
    val stack = makeStack(input)

    var value = stack.removeFirst().first()
    while (stack.isNotEmpty()) {
        value = stack.removeFirst().first() - value
    }

    return value
}

fun makeStack(input: List<Int>): ArrayDeque<List<Int>> {
    val stack = ArrayDeque<List<Int>>()
    stack.add(input)

    while (!stack.first().all { it == 0 }) {
        stack.addFirst(stack.first().differenceList())
    }
    return stack
}

fun main() {
    fun part1(input: List<String>): Int = input.map { parseLine(it) }.sumOf { nextValue(it) }

    fun part2(input: List<String>): Int = input.map { parseLine(it) }.sumOf { previousValue(it) }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    check(part1(testInput) == 114)
    check(part2(testInput) == 2)

    val input = readInput("Day09")
    check(part1(input).alsoPrintln() == 1955513104)
    check(part2(input).alsoPrintln() == 1131)
}

