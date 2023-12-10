data class Node(val name: String, val left: String, val right: String) {
    fun step(direction: Char): String = when (direction) {
        'L' -> left
        'R' -> right
        else -> throw Exception("You can only go left or right, not $direction")
    }
}
fun parse(input: List<String>): Pair<String, Map<String, Node>> {
    val steps = input[0]
    val network = mutableMapOf<String, Node>()

    for (line in input.slice(2..<input.size)) {
        val (name, children) = line.split('=').map { it.trim() }
        val (left, right) = children.trim('(', ')').split(',').map { it.trim() }
        network[name] = Node(name, left, right)
    }

    return Pair(steps, network)

}
fun countSteps(instructions: String, network: Map<String, Node>, startNode: String, endCondition: (String) -> Boolean): Int {
    var i = 0
    var count = 0
    var currentNode = network[startNode]!!
    while (!endCondition(currentNode.name)) {
        i = i.mod(instructions.length)
        val direction = instructions[i++]
        currentNode = network[currentNode.step(direction)]!!
        count++
    }
    return count
}
fun main() {

    fun part1(input: List<String>): Int {
        val (instructions, network) = parse(input)
        return countSteps(instructions, network, "AAA") { it == "ZZZ" }
    }

    fun part2(input: List<String>): Int {
        val (instructions, network) = parse(input)
        println(instructions)

        val startNodes = network.keys.filter { it.last() == 'A' }.toMutableList()
        val counts = startNodes.map { node -> countSteps(instructions, network, node) { it.last() == 'Z' } }

        println(counts) // And then I went and asked Wolfram Alpha what the least common multiple is.
        return 0
    }

    val input = readInput("Day08")
    check(part1(input) == 17141)
    part2(input).println() // 10818234074807
}
