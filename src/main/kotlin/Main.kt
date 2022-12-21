fun main() {
    val dayNumber = 3
    val test = false
    val data = readInput("day3_${if (test) "test" else "input"}")

    val day = Class.forName("Day$dayNumber").kotlin.objectInstance as Day

    day.part1(data).println()
    day.part2(data).println()
}