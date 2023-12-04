fun main() {
    val numberCheck = Regex("\\d+")
    val isSymbol = Regex("[^\\d.]")

    fun String.hasSymbol(): Boolean {
        return isSymbol.containsMatchIn(this)
    }

    fun part1(rows: List<String>): Int {
        var sum = 0

        val height = rows.size
        val width = rows[0].length

        for (i in 0..<height) {
            val currentRow = rows[i]
            var j = 0
            while (j < width) {
                val numberMatch = numberCheck.matchAt(currentRow, j)
                if (numberMatch != null) {
                    val number = numberMatch.value.toInt()
                    val numberWidth = numberMatch.value.length

                    // We've found a number, now we need to go search for symbols.
                    val startColumn = (j-1).coerceAtLeast(0)
                    val endColumn = (j+numberWidth).coerceAtMost(width-1)

                    if (currentRow.slice(startColumn..endColumn).hasSymbol()
                        || rows.getOrNull(i-1)?.slice(startColumn..endColumn)?.hasSymbol() == true
                        || rows.getOrNull(i+1)?.slice(startColumn..endColumn)?.hasSymbol() == true
                    ) {
                        sum += number
                    }
                    j += numberWidth
                } else {
                    j++
                }
            }
        }
        return sum
    }

    fun IntRange.extend(n: Int): IntRange {
        return IntRange(this.first-n, this.last+n)
    }


    // Thanks, https://stackoverflow.com/questions/67501828/get-index-of-all-occurrences-in-a-list
    // TIL about takeIf
    fun String.indicesOf(element: Char) = this.mapIndexedNotNull { index, e -> index.takeIf { element == e } }

    fun part2(rows: List<String>): Int {
        var sum = 0

        val height = rows.size
        val width = rows[0].length

        val allNumbers = rows.map { numberCheck.findAll(it) }

        for (i in 0..<height) {
            val currentRow = rows[i]
            val starIndices = currentRow.indicesOf('*')
            for (starIndex in starIndices) {

                val contiguousNumbers = allNumbers
                    .slice((i - 1).coerceAtLeast(0)..(i + 1).coerceAtMost(width - 1))
                    .map { it.toList() }
                    .flatten()
                    .filter { it.range.extend(1).contains(starIndex) }
                if (contiguousNumbers.size == 2) {
                    val product = contiguousNumbers.map { it.value.toInt() }
                        .fold(1) { acc, n -> acc * n }
                    sum += product
                }
            }
        }

        return sum
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 4361)
    check(part2(testInput) == 467835)

    val input = readInput("Day03")
    check(part1(input) == 546312)
    part2(input).println()
}
