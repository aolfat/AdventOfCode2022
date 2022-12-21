fun main() {
    val dayNumber = 2
    val data = readInput("day2_input")

    val day = Class.forName("Day$dayNumber").kotlin.objectInstance as Day

    day.part1(data).println()
    day.part2(data).println()
}