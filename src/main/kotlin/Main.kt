fun main() {
    val dayNumber = 7
    val test = true
    val data = readInput("day${dayNumber}_${if (test) "test" else "input"}")

    val day = Class.forName("Day$dayNumber").kotlin.objectInstance as Day

    day.part1(data).println()
    day.part2(data).println()
}