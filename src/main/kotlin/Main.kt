fun main() {
    val dayNumber = 1
    val data = readInput("input")

    val day = Class.forName("Day$dayNumber").kotlin.objectInstance as Day

    day.part1(data).println()
    day.part2(data).println()
}