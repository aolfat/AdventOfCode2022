//fun main() {
//    val input = "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw"
//    val index = input.windowedSequence(4, 1).indexOfFirst { s ->
//        s.toSet().size == 4
//    }
//    println(index + 4)
//
//}

object Day6: Day() {
    override fun part1(input: String): Any? {
        return windowedFirstUniqueChars(input, 4)
    }

    private fun windowedFirstUniqueChars(input: String, size: Int): Int {
        val index = input.windowedSequence(size, 1).indexOfFirst { s ->
            s.toSet().size == size
        }
        return index + size
    }

    override fun part2(input: String): Any? =
        windowedFirstUniqueChars(input, 14)


}